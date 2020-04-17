package mrs.app.reservation;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import mrs.app.validation.EndTimeMustBeAfterStartTime;
import mrs.app.validation.ThirtyMinutesUnit;

@Data
@EndTimeMustBeAfterStartTime
public class ReservationEditForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String reservationId;
	
	private Integer roomId;

	@NotNull(message = "必須です")
	@ThirtyMinutesUnit
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;

	@NotNull(message = "必須です")
	@ThirtyMinutesUnit
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate reservedDate;
}