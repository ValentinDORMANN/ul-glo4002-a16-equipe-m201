package ca.ulaval.glo4002.flycheckin.reservation.rest;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.flycheckin.reservation.domain.CheckinService;
import ca.ulaval.glo4002.flycheckin.reservation.exception.NotFoundPassengerException;
import ca.ulaval.glo4002.flycheckin.reservation.exception.ReservationModuleException;
import ca.ulaval.glo4002.flycheckin.reservation.rest.dto.CheckinDto;

public class CheckinResourceTest {

  private static final int BAD_REQUEST = 400;
  private static final int NOT_FOUND = 404;
  private CheckinService mockCheckinService;
  private UriInfo uriInfoMock;
  private CheckinDto checkinDtoMock;
  private CheckinResource checkinResource;

  @Before
  public void initiateTest() {
    mockCheckinService = mock(CheckinService.class);
    checkinResource = new CheckinResource(mockCheckinService);
    checkinDtoMock = mock(CheckinDto.class);
  }

  @Test
  public void givenPassengerHashNotInMemoryWhenCheckinThenReturnStatusNotFound() {
    willThrow(NotFoundPassengerException.class).given(mockCheckinService).saveCheckin(checkinDtoMock);

    Response response = checkinResource.createCheckin(uriInfoMock, checkinDtoMock);

    assertEquals(NOT_FOUND, response.getStatus());
  }

  @Test
  public void givenIncompletePassengerWhenCheckinThenReturnStatusBadRequest() {
    willThrow(ReservationModuleException.class).given(mockCheckinService).saveCheckin(checkinDtoMock);

    Response response = checkinResource.createCheckin(uriInfoMock, checkinDtoMock);

    assertEquals(BAD_REQUEST, response.getStatus());
  }
}
