package ca.ulaval.glo4002.flycheckin.boarding.services.interne;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.flycheckin.boarding.domain.Seat;
import ca.ulaval.glo4002.flycheckin.boarding.exception.NoSeatAvailableException;

public class SeatAssignationRandomStrategyTest {

  private static final int SEAT_NUMBER_ROW = 13;
  private static final String SEAT_NUMBER_COLUMN = "K";
  private static final String SEAT_NUMBER = "13-K";
  private static final String SEAT_CLASS = "economic";
  private static final String ANOTHER_SEAT_CLASS = "wrongSeatClass";
  private Seat seat;
  private List<Seat> availableSeats;
  private SeatAssignationRandomStrategy randomSeatAssignation;

  @Before
  public void initiateTest() {
    availableSeats = new ArrayList<Seat>();
    randomSeatAssignation = new SeatAssignationRandomStrategy();
  }

  @Test(expected = NoSeatAvailableException.class)
  public void givenEmptyAvailableSeatListWhenChooseSeatNumberThenReturnException() {
    randomSeatAssignation.chooseSeatNumber(availableSeats, SEAT_CLASS);
  }

  @Test
  public void givenListWithOnlyOneSeatWhenChooseSeatNumberWithSameSeatClassThenReturnSeatNumber() {
    seat = new Seat();
    seat.setSeatNumber(SEAT_NUMBER_ROW, SEAT_NUMBER_COLUMN);
    seat.setSeatClass(SEAT_CLASS);
    availableSeats.add(seat);

    String seatNumber = randomSeatAssignation.chooseSeatNumber(availableSeats, SEAT_CLASS);

    assertEquals(SEAT_NUMBER, seatNumber);
  }

  @Test(expected = NoSeatAvailableException.class)
  public void givenListWithOnlyOneSeatWhenChooseSeatNumberWithAnotherSeatClassThenReturnException() {
    seat = new Seat();
    seat.setSeatNumber(SEAT_NUMBER_ROW, SEAT_NUMBER_COLUMN);
    seat.setSeatClass(SEAT_CLASS);
    availableSeats.add(seat);

    randomSeatAssignation.chooseSeatNumber(availableSeats, ANOTHER_SEAT_CLASS);
  }
}