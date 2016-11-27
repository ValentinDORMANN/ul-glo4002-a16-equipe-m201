package ca.ulaval.glo4002.flycheckin.reservation.domain;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.flycheckin.reservation.rest.dto.PassengerDto;

public class PassengerTest {

  private static final int CHILD_AGE = 10;
  private static final int ADULT_AGE = 30;
  private static final String FIRST_NAME = "FirstName";
  private static final String LAST_NAME = "LastName";
  private static final String PASSPORT_NUMBER = "NUMBER";
  private static final String EMPTY_STRING = "";
  private static final boolean IS_VIP = true;
  private PassengerDto passengerDtoMock;
  private PassengerDto wrongPassengerDtoMock;
  private Passenger passenger;
  private Passenger fakePassenger;

  @Before
  public void initiateTest() {
    passengerDtoMock = mock(PassengerDto.class);
    wrongPassengerDtoMock = mock(PassengerDto.class);
    passengerDtoMock.age = CHILD_AGE;
    passengerDtoMock.first_name = FIRST_NAME;
    passengerDtoMock.last_name = LAST_NAME;
    passengerDtoMock.passport_number = PASSPORT_NUMBER;
    wrongPassengerDtoMock.age = ADULT_AGE;
    wrongPassengerDtoMock.first_name = EMPTY_STRING;
    wrongPassengerDtoMock.last_name = EMPTY_STRING;
    wrongPassengerDtoMock.passport_number = EMPTY_STRING;
    passenger = new Passenger(passengerDtoMock);
    fakePassenger = new Passenger(wrongPassengerDtoMock);
  }

  @Test
  public void givenChildPassengerDtoWhenCheckIfChildThenReturnTrue() {
    assertTrue(passenger.isChild());
  }

  @Test
  public void givenAdultPassengerDtoWhenCheckIfChildThenReturnFalse() {
    assertFalse(fakePassenger.isChild());
  }

  @Test
  public void givenPassengerWhenCheckIfIsValidThenReturnTrue() {
    assertTrue(passenger.isValid());
  }

  @Test
  public void givenIncompleteFirstNameWhenCheckIfValidThenReturnFalse() {
    passengerDtoMock.first_name = EMPTY_STRING;
    passenger = new Passenger(passengerDtoMock);

    assertFalse(passenger.isValid());
  }

  @Test
  public void givenIncompleteLastNameWhenCheckIfValidThenReturnFalse() {
    passengerDtoMock.last_name = EMPTY_STRING;
    passenger = new Passenger(passengerDtoMock);

    assertFalse(passenger.isValid());
  }

  @Test
  public void givenIncompletePassportNumberWhenCheckIfValidThenReturnFalse() {
    passengerDtoMock.passport_number = EMPTY_STRING;
    passenger = new Passenger(passengerDtoMock);

    assertFalse(passenger.isValid());
  }

  @Test
  public void givenWrongPassengerWhenCheckIfValidThenReturnFalse() {
    assertFalse(fakePassenger.isValid());
  }

  @Test
  public void givenNewPassengerWhenGetVipStatusThenReturnFalse() {
    assertFalse(passenger.getIsVip());
  }

  @Test
  public void givenVipPassengerWhenGetVipStatusTHenReturnTrue() {
    passenger.changeVipStatus(IS_VIP);

    assertTrue(passenger.getIsVip());
  }
}
