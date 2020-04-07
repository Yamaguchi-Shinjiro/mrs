package mrs.domain.exception;

public class UnavailableReservationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnavailableReservationException(String message) {
		super(message);
	}
}