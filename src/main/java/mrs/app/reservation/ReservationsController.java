package mrs.app.reservation;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import mrs.domain.exception.AlreadyReservedException;
import mrs.domain.exception.UnavailableReservationException;
import mrs.domain.model.*;
import mrs.domain.service.reservation.*;
import mrs.domain.service.room.RoomService;
import mrs.domain.service.user.ReservationUserDetails;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("reservations")
public class ReservationsController {
	@Autowired
	RoomService roomService;
	@Autowired
	ReservationService reservationService;

	@ModelAttribute
	ReservationListForm setUpReservationListForm() {
		return new ReservationListForm();
	}

	@ModelAttribute
	ReservationEditForm setUpReservationEditForm() {
		ReservationEditForm form = new ReservationEditForm();
		// デフォルト値
//		form.setStartTime(LocalTime.of(9, 0));
//		form.setEndTime(LocalTime.of(10, 0));
		return form;
	}

	@GetMapping
	String index(Model model,
			@PageableDefault(size = 5) @SortDefaults({ @SortDefault(sort = "reservedDate", direction = Direction.ASC),
					@SortDefault(sort = "startTime", direction = Direction.ASC) }) Pageable pageable) {
		Page<Reservation> reservations = reservationService.findList(pageable);
		model.addAttribute("page", reservations);
		model.addAttribute("reservations", reservations.getContent());
		return "reservation/list";
	}

	@GetMapping("new")
	String newReservation(Model model) {
		initialize(model);
		return "reservation/new";
	}

	@PostMapping
	String create(@Validated ReservationEditForm form, BindingResult bindingResult,
			@AuthenticationPrincipal ReservationUserDetails userDetails, Model model) {
		if (bindingResult.hasErrors()) {
			initialize(model);
			return "reservation/new";
		}
		Reservation reservation = new Reservation();
		reservation.setStartTime(form.getStartTime());
		reservation.setEndTime(form.getEndTime());
		reservation.setUser(userDetails.getUser());
		MeetingRoom room = new MeetingRoom();
		room.setRoomId(form.getRoomId());
		reservation.setMeetingRoom(room);
		reservation.setReservedDate(form.getReservedDate());
		try {
			reservationService.create(reservation);
		} catch (UnavailableReservationException | AlreadyReservedException e) {
			model.addAttribute("error", e.getMessage());
			return newReservation(model);
		}
		return "redirect:/reservations";
	}

	@GetMapping("{reservationId}/edit")
	String editReservation(@PathVariable Integer reservationId, Model model) {
		initialize(model);
		Reservation reservation = reservationService.findOne(reservationId);
		model.addAttribute("reservation", reservation);
		return "reservation/edit";
	}

	@PostMapping("{reservationId}/edit")
	String update(@Validated ReservationEditForm form, BindingResult bindingResult,
			@AuthenticationPrincipal ReservationUserDetails userDetails, @PathVariable Integer reservationId,
			Model model) {
		if (bindingResult.hasErrors()) {
			initialize(model);
			Reservation reservation = reservationService.findOne(reservationId);
			BeanUtils.copyProperties(form, reservation);
			reservation.getMeetingRoom().setRoomId(form.getRoomId());
			model.addAttribute("reservation", reservation);
			return "reservation/edit";
		}
		Reservation reservation = new Reservation();
		reservation.setReservationId(reservationId);
		reservation.setStartTime(form.getStartTime());
		reservation.setEndTime(form.getEndTime());
		reservation.setUser(userDetails.getUser());
		MeetingRoom room = new MeetingRoom();
		room.setRoomId(form.getRoomId());
		reservation.setMeetingRoom(room);
		reservation.setReservedDate(form.getReservedDate());
		try {
			reservationService.edit(reservation);
		} catch (UnavailableReservationException | AlreadyReservedException e) {
			model.addAttribute("error", e.getMessage());
			return editReservation(reservationId, model);
		}
		return "redirect:/reservations";
	}

	@PostMapping("cancel")
	String cancel(@RequestParam("reservationId") Integer reservationId, Model model, Pageable pageable) {
		try {
			Reservation reservation = reservationService.findOne(reservationId);
			reservationService.cancel(reservation);
		} catch (AccessDeniedException e) {
			model.addAttribute("error", e.getMessage());
			return index(model, pageable);
		}
		return "redirect:/reservations";
	}
	
	void initialize(Model model) {
		List<LocalTime> timeList = Stream.iterate(LocalTime.of(0, 0), t -> t.plusMinutes(30)).limit(24 * 2)
				.collect(Collectors.toList());
		model.addAttribute("timeList", timeList);
		List<MeetingRoom> rooms = roomService.findList();
		model.addAttribute("rooms", rooms);
	}
}