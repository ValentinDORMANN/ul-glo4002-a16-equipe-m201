package ca.ulaval.glo4002.flycheckin.flycheckin_uat.steps;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.ulaval.glo4002.flycheckin.boarding.client.AmsMapEncoded;
import ca.ulaval.glo4002.flycheckin.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flycheckin.boarding.domain.passenger.PassengerFactory;
import ca.ulaval.glo4002.flycheckin.boarding.domain.seat.NoSeatAvailableException;
import ca.ulaval.glo4002.flycheckin.boarding.domain.seat.Seat;

import ca.ulaval.glo4002.flycheckin.boarding.services.external.PlaneModelService;
import ca.ulaval.glo4002.flycheckin.reservation.domain.Reservation;
import ca.ulaval.glo4002.flycheckin.reservation.persistence.CheckinInMemory;
import ca.ulaval.glo4002.flycheckin.reservation.rest.dto.PassengerDto;
import ca.ulaval.glo4002.flycheckin.reservation.rest.dto.ReservationDto;
import cucumber.api.java8.En;

public class SeatAssignationSteps implements En {
  private static final int AGE = 18;
  private static final String FIRST_NAME = "Alice";
  private static final String SEAT_CLASS = "economy";
  private static final String PASSPORT_NUMBER = "AL987";
  private static final int RESERVATION_NUMBER = 38000;
  private static final String RESERVATION_DATE = "2016-12-15";
  private static final String RESERVATION_CONFIRMATION = "98765";
  private static final String FLIGHT_DATE = "2017-01-15T00:00:00Z";
  private static final String PAYEMENT_LOCATION = "/payments/kjshkjhdkjnkd";
  private static final String NO_SEAT_AVAILABLE = "Aucun siège de la catégorie du passager n'est disponible";
  private static final SimpleDateFormat DATE_FORMAT_COURT = new SimpleDateFormat("yyyy-MM-dd");
  private static final SimpleDateFormat DATE_FORMAT_LONG = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

  PassengerDto passengerDto = new PassengerDto();
  ReservationDto reservationDto = new ReservationDto();
  Passenger passenger;
  Reservation reservation;
  SeatAssignation seatAssignation = null;

  @cucumber.api.java.Before
  public void beforeScenario() throws ParseException {
    Date reservationDate = DATE_FORMAT_COURT.parse(RESERVATION_DATE);
    Date flightDate = DATE_FORMAT_LONG.parse(FLIGHT_DATE);

    reservationDto.reservation_number = RESERVATION_NUMBER;
    reservationDto.reservation_date = reservationDate;
    reservationDto.reservation_confirmation = RESERVATION_CONFIRMATION;
    reservationDto.flight_date = flightDate;
    reservationDto.payment_location = PAYEMENT_LOCATION;

    passengerDto.first_name = FIRST_NAME;
    passengerDto.age = AGE;
    passengerDto.passport_number = PASSPORT_NUMBER;
    passengerDto.seat_class = SEAT_CLASS;
  }

	private static final int AGE = 18;
	private static final String LAST_NAME = "Alice";
	private static final String SEAT_CLASS = "economy";
	private static final String PASSPORT_NUMBER = "AL987";
	private static final int RESERVATION_NUMBER = 38000;
	private static final String RESERVATION_DATE = "2016-12-15";
	private static final String RESERVATION_CONFIRMATION = "98765";
	private static final String FLIGHT_DATE = "2017-01-15T00:00:00Z";
	private static final String NO_SEAT_AVAILABLE = "Aucun siège de la catégorie du passager n'est disponible";
	private static final SimpleDateFormat DATE_FORMAT_COURT = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat DATE_FORMAT_LONG = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	private PassengerDto passengerDto = new PassengerDto();
	private ReservationDto reservationDto = new ReservationDto();
	private Passenger passenger;
	private Reservation reservation;

	@cucumber.api.java.Before
	public void beforeScenario() throws ParseException {
		Date reservationDate = DATE_FORMAT_COURT.parse(RESERVATION_DATE);
		Date flightDate = DATE_FORMAT_LONG.parse(FLIGHT_DATE);

		reservationDto.reservation_number = RESERVATION_NUMBER;
		reservationDto.reservation_date = reservationDate;
		reservationDto.reservation_confirmation = RESERVATION_CONFIRMATION;
		reservationDto.flight_date = flightDate;

		passengerDto.last_name = LAST_NAME;
		passengerDto.age = AGE;
		passengerDto.passport_number = PASSPORT_NUMBER;
		passengerDto.seat_class = SEAT_CLASS;

	}

	public SeatAssignationSteps() {
		Given("^a passenger \"([^\"]*)\" with a reservation on a flight$", (String passengerName) -> {
			passenger = createPassengerWithReservation(passengerName);
		});

		Given("^seats are available on this flight$", () -> {
			PlaneModelService planeModelService = new PlaneModelService();
			AmsMapEncoded amsMapConnector = new AmsMapEncoded();
			String planeModel = amsMapConnector.getPlaneModelByFlightNumber(passenger.getFlightNumber());
			List<Seat> seats = planeModelService.getSeatsAccordingPlaneModel(planeModel);
			seats = filterSeatsBySeatClass(seats, passenger.getSeatClass());
		});

		Given("^he already done his check-in$", () -> {
			CheckinInMemory checkinInMemory = new CheckinInMemory();
			checkinInMemory.doPassengerCheckin(passenger.getPassengerHash());
		});

		When("^he chooses his seat wiht the \"([^\"]*)\" mode$", (String mode) -> {

		});

		Then("^a seat is assigned to him on that flight$", () -> {

		});
	}

	public Passenger createPassengerWithReservation(String passengerName) {
		reservationDto.passengers = new ArrayList<PassengerDto>();
		passengerDto.first_name = passengerName;
		reservationDto.passengers.add(passengerDto);
		reservation = new Reservation(reservationDto);
		return createBoardingPassenger(reservation);
	}

	private Passenger createBoardingPassenger(Reservation reservation) {
		PassengerFactory passengerFactory = new PassengerFactory();
		String passengerHash = reservation.getPassengers().get(0).getPassengerHash();
		String seatClass = reservation.getPassengers().get(0).getSeatClass();
		return passengerFactory.createPassenger(reservation.getFlightNumber(), reservation.getFlightDate(),
				passengerHash, seatClass, false, false);
	}

	private List<Seat> filterSeatsBySeatClass(List<Seat> seats, String seatClass) {
		List<Seat> filterSeats = new ArrayList<Seat>();
		for (Seat seat : seats) {
			if (seat.hasClass(seatClass))
				filterSeats.add(seat);
		}
		if (filterSeats.isEmpty()) {
			throw new NoSeatAvailableException(NO_SEAT_AVAILABLE);
		}
		return filterSeats;
	}
}
