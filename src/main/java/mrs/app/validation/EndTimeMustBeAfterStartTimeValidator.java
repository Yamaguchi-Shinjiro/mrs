package mrs.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import mrs.app.reservation.ReservationEditForm;

public class EndTimeMustBeAfterStartTimeValidator
		implements ConstraintValidator<EndTimeMustBeAfterStartTime, ReservationEditForm> {
	private String message;

	@Override
	public void initialize(EndTimeMustBeAfterStartTime constraintAnnotation) {
		message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(ReservationEditForm value, ConstraintValidatorContext context) {
		if (value.getStartTime() == null || value.getEndTime() == null) {
			return true;
		}
		boolean isEndTimeMustBeAfterStartTime = value.getEndTime().isAfter(value.getStartTime());
		if (!isEndTimeMustBeAfterStartTime) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addPropertyNode("endTime").addConstraintViolation();
		}
		return isEndTimeMustBeAfterStartTime;
	}
}