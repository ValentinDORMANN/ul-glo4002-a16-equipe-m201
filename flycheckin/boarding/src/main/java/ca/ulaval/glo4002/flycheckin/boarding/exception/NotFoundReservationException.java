package ca.ulaval.glo4002.flycheckin.boarding.exception;

public class NotFoundReservationException extends FlyCheckinApplicationException {

  private static final long serialVersionUID = 1L;

  public NotFoundReservationException() {
  }

  public NotFoundReservationException(String message) {
    super(message);
  }
}
