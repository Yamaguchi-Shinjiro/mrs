package mrs.domain.repository.reservation;

import java.time.LocalDate;
import java.util.List;

import mrs.domain.model.MeetingRoom;
import mrs.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	List<Reservation> findByMeetingRoomIsAndReservedDateOrderByStartTime(MeetingRoom meetingRoom, LocalDate reservedDate);
	
	List<Reservation> findByMeetingRoomIsAndReservedDateAndReservationIdNotOrderByStartTime(MeetingRoom meetingRoom, LocalDate reservedDate, Integer reservationId);
}