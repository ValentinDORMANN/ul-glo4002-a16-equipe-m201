package ca.ulaval.glo4002.flycheckin.boarding.client;

import ca.ulaval.glo4002.flycheckin.boarding.exception.BoardingModuleException;

public class NotCheckedinException extends BoardingModuleException {

  private static final long serialVersionUID = 1L;

  public NotCheckedinException() {
  }

  public NotCheckedinException(String message) {
    super(message);
  }
}
