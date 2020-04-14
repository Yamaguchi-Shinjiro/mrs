package mrs.domain.exception;

public class AlreadyRegisteredReservationsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AlreadyRegisteredReservationsException(String message) {
		super(message);
	}
}