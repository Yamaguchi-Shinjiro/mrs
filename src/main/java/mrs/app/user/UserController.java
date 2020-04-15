package mrs.app.user;

import java.util.List;

import javax.validation.groups.Default;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import mrs.app.user.UserEditForm.NewUser;
import mrs.domain.exception.AlreadyRegisteredReservationsException;
import mrs.domain.model.RoleName;
import mrs.domain.model.User;
import mrs.domain.service.user.ReservationUserDetails;
import mrs.domain.service.user.UserService;

@Controller
@RequestMapping("users")
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	SmartValidator validator;
	
	@ModelAttribute
	public UserListForm setUpUserListForm() {
		return new UserListForm();
	}

	@ModelAttribute
	public UserEditForm setUpUserEditForm() {
		return new UserEditForm();
	}
	
	@GetMapping
	public String index(Model model) {
		List<User> users = userService.findUsers();
		model.addAttribute("users", users);
		return "user/list";
	}
	
	@GetMapping("new")
	String newUser(Model model) {
		model.addAttribute("roleNameList", RoleName.values());
		return "user/new";
	}

	@GetMapping("{userId}/edit")
	String edit(@PathVariable String userId, Model model) {
		model.addAttribute("roleNameList", RoleName.values());
		User user = userService.findUser(userId);
		model.addAttribute("user", user);
		return "user/edit";
	}

	@PostMapping
	String create(@Validated({Default.class, NewUser.class}) UserEditForm form, BindingResult bindingResult,
			@AuthenticationPrincipal ReservationUserDetails userDetails,
			Model model) {
		if (bindingResult.hasErrors()) {
			return newUser(model);
		}
		User user = new User();
		BeanUtils.copyProperties(form, user);
		userService.regist(user);
		return "redirect:/users";
	}

	@PostMapping("{userId}/edit")
	String update(@Validated UserEditForm form, BindingResult bindingResult,
			@AuthenticationPrincipal ReservationUserDetails userDetails,
			@PathVariable("userId") String userId,
			Model model) {
		if (bindingResult.hasErrors()) {
			return edit(userId, model);
		}
		User user = new User();
		BeanUtils.copyProperties(form, user);
		userService.update(user);
		return "redirect:/users";
	}
	
	@PostMapping("{userId}/destroy")
	public String destroy(UserListForm form,
			@AuthenticationPrincipal ReservationUserDetails userDetails,
			@PathVariable("userId") String userId,
			Model model) {
		try {
			userService.delete(userId);
		} catch (AlreadyRegisteredReservationsException e) {
			model.addAttribute("error", e.getMessage());
			return index(model);
		}
		return "redirect:/users";
	}
}