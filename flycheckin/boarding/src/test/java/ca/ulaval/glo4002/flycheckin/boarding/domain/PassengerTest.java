package ca.ulaval.glo4002.flycheckin.boarding.domain;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.flycheckin.boarding.exception.ExcededCheckedLuggageException;
import ca.ulaval.glo4002.flycheckin.boarding.rest.dto.PassengerDto;
import ca.ulaval.glo4002.flycheckin.boarding.rest.dto.ReservationDto;

public class PassengerTest {

  private static final String FLIGHT_NUMBER = "A320";
  private static final Date FLIGHT_DATE = new Date();
  private static final String SEAT_CLASS = "business";
  private static final String PASSENGER_HASH = "PassengerHash";
  private static final String TYPE_CHECKED = "checked";
  private static final int LIMIT_CHECKED_LUGGAGES = 3;
  private static final boolean IS_CHECKED = true;

  private Passenger passenger;
  private PassengerDto mockPassengerDto;
  private ReservationDto mockReservationDto;
  private Luggage mockLuggage;

  @Before
  public void initiateTest() {
    mockPassengerDto = mock(PassengerDto.class);
    mockPassengerDto.seat_class = SEAT_CLASS;
    mockPassengerDto.passenger_hash = PASSENGER_HASH;
    mockReservationDto = mock(ReservationDto.class);
    mockReservationDto.flight_number = FLIGHT_NUMBER;
    mockReservationDto.flight_date = FLIGHT_DATE;
    PassengerDto[] passengers = { mockPassengerDto };
    mockReservationDto.passengers = passengers;
    passenger = new Passenger(mockReservationDto);
    mockLuggage = mock(Luggage.class);
    willReturn(IS_CHECKED).given(mockLuggage).isType(TYPE_CHECKED);
  }

  @Test(expected = ExcededCheckedLuggageException.class)
  public void givenManyLuggageWhenAddItThenPassengerHaveIt() {
    for (int i = 0; i <= LIMIT_CHECKED_LUGGAGES; i++) {
      passenger.addLuggage(mockLuggage);
    }
  }
}