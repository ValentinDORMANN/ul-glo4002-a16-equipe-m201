package ca.ulaval.glo4002.flycheckin.reservation.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.flycheckin.reservation.exception.NotCheckedinException;
import ca.ulaval.glo4002.flycheckin.reservation.exception.NotFoundPassengerException;
import ca.ulaval.glo4002.flycheckin.reservation.exception.NotTimeToCheckinException;
import ca.ulaval.glo4002.flycheckin.reservation.exception.ReservationModuleException;
import ca.ulaval.glo4002.flycheckin.reservation.persistence.CheckinInMemory;
import ca.ulaval.glo4002.flycheckin.reservation.persistence.ReservationInMemory;
import ca.ulaval.glo4002.flycheckin.reservation.rest.dto.CheckinDto;

public class CheckinServiceTest {

  private static final String PASSENGER_HASH = "HASH";
  private static final String FAKE_PASSENGER_HASH = "FAKE_HASH";
  private static final String AGENT = "AGENT";
  private static final String SELF = "SELF";
  private static final int CHECKIN_NUMBER = 1;
  private static final boolean IS_VALID = true;
  private static final boolean IS_NOT_VALID = false;
  private CheckinInMemory checkinInMemoryMock;
  private ReservationInMemory reservationInMemoryMock;
  private CheckinDto checkinDtoMock;
  private Reservation reservationMock;
  private CheckinService checkinService;

  @Before
  public void initiateTest() {
    checkinInMemoryMock = mock(CheckinInMemory.class);
    reservationInMemoryMock = mock(ReservationInMemory.class);
    checkinDtoMock = mock(CheckinDto.class);
    reservationMock = mock(Reservation.class);
    checkinService = new CheckinService(checkinInMemoryMock, reservationInMemoryMock);
    willReturn(reservationMock).given(reservationInMemoryMock).getReservationByPassengerHash(PASSENGER_HASH);
    willThrow(NotFoundPassengerException.class).given(reservationInMemoryMock)
        .getReservationByPassengerHash(FAKE_PASSENGER_HASH);
    checkinDtoMock.passenger_hash = PASSENGER_HASH;
    checkinDtoMock.by = AGENT;
    willReturn(CHECKIN_NUMBER).given(checkinInMemoryMock).doPassengerCheckin(PASSENGER_HASH);
  }

  @Test(expected = NotFoundPassengerException.class)
  public void givenFakePassengerWhenAgentCheckinThenThrowException() {
    checkinDtoMock.passenger_hash = FAKE_PASSENGER_HASH;

    checkinService.saveCheckin(checkinDtoMock);
  }

  @Test(expected = NotTimeToCheckinException.class)
  public void givenPassengerWhenSelfCheckinNotInTimeThenThrowException() {
    checkinDtoMock.by = SELF;
    willThrow(NotTimeToCheckinException.class).given(reservationMock).validateCheckinPeriod(checkinDtoMock.by);

    checkinService.saveCheckin(checkinDtoMock);
  }

  @Test(expected = ReservationModuleException.class)
  public void givenWrongPassengerInformationWhenCheckinThenThrowException() {
    willReturn(IS_NOT_VALID).given(reservationMock).isPassengerInfosValid(PASSENGER_HASH);

    checkinService.saveCheckin(checkinDtoMock);
  }

  @Test
  public void givenValidPassengerWhenDoCheckinThenReturnCheckinNumber() {
    willReturn(IS_VALID).given(reservationMock).isPassengerInfosValid(PASSENGER_HASH);

    int checkinNumber = checkinService.saveCheckin(checkinDtoMock);

    assertEquals(CHECKIN_NUMBER, checkinNumber);
  }

  @Test
  public void givenPassengerNotCheckedWhenIsCheckinDoneThenVerifyCheckinServiceCalled() {
    checkinService.isCheckInPassengerDone(PASSENGER_HASH);

    verify(checkinInMemoryMock).isCheckinDone(PASSENGER_HASH);
  }

  @Test(expected = NotCheckedinException.class)
  public void givenPassengerNotCheckedWhenIsCheckinDoneThenThrowException() {
    willThrow(NotCheckedinException.class).given(checkinInMemoryMock).isCheckinDone(PASSENGER_HASH);

    checkinService.isCheckInPassengerDone(PASSENGER_HASH);
  }
}
