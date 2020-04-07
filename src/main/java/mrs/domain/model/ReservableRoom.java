package mrs.domain.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class ReservableRoom implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private ReservableRoomId reservableRoomId;
	@ManyToOne
	@JoinColumn(name = "room_id", insertable = false, updatable = false)
	@MapsId("roomId")
	private MeetingRoom meetingRoom;

	public ReservableRoom(ReservableRoomId reservableRoomId) {
		this.reservableRoomId = reservableRoomId;
	}

	public ReservableRoom() {
	}
}