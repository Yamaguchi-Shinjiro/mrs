package mrs.app.product;

import mrs.domain.model.*;
import mrs.domain.service.product.ProductService;
import mrs.domain.service.user.ReservationUserDetails;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("products")
public class ProductController {
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@ModelAttribute
	ProductListForm setUpProductListForm() {
		return new ProductListForm();
	}

	@ModelAttribute
	ProductEditForm setUpProductEditForm() {
		return new ProductEditForm();
	}

	@GetMapping
	String index(Model model, @PageableDefault(size = 10) @SortDefaults({
			@SortDefault(sort = "productId", direction = Direction.ASC) }) Pageable pageable) {
		Page<Product> products = productService.findList(pageable);
		model.addAttribute("page", products);
		model.addAttribute("products", products.getContent());
		return "product/list";
	}

	@GetMapping("new")
	String newProduct(Model model) {
		return "product/new";
	}

	@PostMapping
	String create(@Validated ProductEditForm form, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "product/new";
		}
		Product product = new Product();
		BeanUtils.copyProperties(form, product);
		productService.create(product);
		return "redirect:/products";
	}

	@GetMapping("edit/{productId}")
	String editProduct(ProductEditForm form, @PathVariable String productId, Model model) {
		Product product = productService.findOne(productId);
		BeanUtils.copyProperties(product, form);
		return "product/edit";
	}

	@PostMapping("edit/{productId}")
	String update(@Validated ProductEditForm form, BindingResult bindingResult,
			@AuthenticationPrincipal ReservationUserDetails userDetails, @PathVariable String productId,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "product/edit";
		}
		Product product = new Product();
		BeanUtils.copyProperties(form, product);
		productService.edit(product);
		return "redirect:/products";
	}

	@PostMapping("cancel")
	String cancel(@RequestParam("productId") String productId, Model model, Pageable pageable) {
		try {
			Product product = productService.findOne(productId);
			productService.cancel(product);
		} catch (AccessDeniedException e) {
			model.addAttribute("error", e.getMessage());
			return index(model, pageable);
		}
		return "redirect:/products";
	}
}