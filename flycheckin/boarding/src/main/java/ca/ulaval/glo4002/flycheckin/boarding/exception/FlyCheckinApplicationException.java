package ca.ulaval.glo4002.flycheckin.boarding.exception;

public class FlyCheckinApplicationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public FlyCheckinApplicationException() {
  }

  public FlyCheckinApplicationException(String message) {
    super(message);
  }

}
