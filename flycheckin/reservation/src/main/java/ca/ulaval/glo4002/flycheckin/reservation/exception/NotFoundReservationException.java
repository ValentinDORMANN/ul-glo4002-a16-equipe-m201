package ca.ulaval.glo4002.flycheckin.reservation.exception;

public class NotFoundReservationException extends ReservationException {

  private static final long serialVersionUID = 1L;

  public NotFoundReservationException(String message) {
    super(message);
  }
}
