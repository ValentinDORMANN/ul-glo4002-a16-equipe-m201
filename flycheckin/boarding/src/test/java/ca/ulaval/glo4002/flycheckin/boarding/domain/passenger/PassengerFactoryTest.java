package ca.ulaval.glo4002.flycheckin.boarding.domain.passenger;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class PassengerFactoryTest {

  private static final String FLIGHT_NUMBER = "AAAA";
  private static final Date FLIGHT_DATE = new Date();
  private static final String PASSENGER_HASH = "hash";
  private static final String ECONOMIC_SEAT_CLASS = "economy";
  private static final String OTHER_SEAT_CLASS = "other";
  private static final boolean IS_VIP = true;
  private static final boolean IS_CHILD = true;

  private PassengerFactory passengerFactory;

  @Before
  public void initiateTest() {
    passengerFactory = new PassengerFactory();
  }

  @Test
  public void givenPassengerWithEconomySeatClassWhenCreatePassengerThenReturnRegularPassenger() {
    Passenger passenger = passengerFactory.createPassenger(FLIGHT_NUMBER, FLIGHT_DATE, PASSENGER_HASH,
        ECONOMIC_SEAT_CLASS, IS_VIP, !IS_CHILD);

    assertTrue(passenger instanceof RegularPassenger);
  }

  @Test
  public void givenPassengerWithOtheThanEconomySeatClassWhenCreatePassengerThenReturnBusinessPassenger() {
    Passenger passenger = passengerFactory.createPassenger(FLIGHT_NUMBER, FLIGHT_DATE, PASSENGER_HASH, OTHER_SEAT_CLASS,
        IS_VIP, !IS_CHILD);

    assertTrue(passenger instanceof BusinessPassenger);
  }
}
