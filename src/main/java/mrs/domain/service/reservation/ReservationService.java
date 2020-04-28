package mrs.domain.service.reservation;

import mrs.domain.exception.AlreadyReservedException;
import mrs.domain.model.*;
import mrs.domain.repository.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("deprecation")
@Service
@Transactional
public class ReservationService {
	@Autowired
	ReservationRepository reservationRepository;

	public Page<Reservation> findList(Pageable pageable) {
		return reservationRepository.findAll(pageable);
	}

	public Reservation create(Reservation reservation) {
		boolean overlap = reservationRepository
				.findByMeetingRoomIsAndReservedDateOrderByStartTime(reservation.getMeetingRoom(),
						reservation.getReservedDate())
				.stream().anyMatch(x -> x.overlap(reservation));
		if (overlap) {
			throw new AlreadyReservedException("入力の時間帯はすでに予約済みです。");
		}
		// 予約情報の登録
		reservationRepository.save(reservation);
		return reservation;
	}

	public Reservation edit(Reservation reservation) {
		boolean overlap = reservationRepository
				.findByMeetingRoomIsAndReservedDateAndReservationIdNotOrderByStartTime(reservation.getMeetingRoom(),
						reservation.getReservedDate(), reservation.getReservationId())
				.stream().anyMatch(x -> x.overlap(reservation));
		if (overlap) {
			throw new AlreadyReservedException("入力の時間帯はすでに予約済みです。");
		}
		// 予約情報の登録
		reservationRepository.save(reservation);
		return reservation;
	}

	@PreAuthorize("hasRole('ADMIN') or #reservation.user.userId == principal.user.userId")
	public void cancel(@P("reservation") Reservation reservation) {
		reservationRepository.delete(reservation);
	}

	public Reservation findOne(Integer reservationId) {
		return reservationRepository.findById(reservationId).orElse(null);
	}
}