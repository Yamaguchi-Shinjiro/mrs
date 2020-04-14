package mrs.app.reservation;

import java.io.Serializable;
import java.time.LocalTime;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import mrs.app.validation.EndTimeMustBeAfterStartTime;
import mrs.app.validation.ThirtyMinutesUnit;

@Data
@EndTimeMustBeAfterStartTime(message = "終了時刻は開始時刻より後にしてください")
public class ReservationForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(message = "必須です")
	@ThirtyMinutesUnit(message = "30分単位で入力してください")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;

	@NotNull(message = "必須です")
	@ThirtyMinutesUnit(message = "30分単位で入力してください")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;
}