package ca.ulaval.glo4002.flycheckin.reservation.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.flycheckin.reservation.domain.Services;
import javassist.NotFoundException;

@Path("/reservations/{reservation_number}")
public class BookingPassengerResource {
	private Services service;
	private JSONObject jsonRequest;
	private boolean validatedJson = true;

	@GET
	public Response getNumberResevation(@Context HttpServletRequest request)
			throws JSONException, ParseException, NumberFormatException, NotFoundException {
		String url_array[] = request.getRequestURL().toString().split("/");
		Services service = new Services();
		JSONObject jsonObject = service.getReservation(Integer.parseInt(url_array[url_array.length - 1]));
		return Response.ok(jsonObject.toString()).build();
	}

	public boolean validateReservationDate(String reservationDate) {
		if (!reservationDate.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$")) {
			validatedJson = false;
		}
		return validatedJson;

	}

	/*
	 * public String extractFlightDate(String flightDate) throws
	 * RuntimeException { String date = flightDate.substring(0, 9); if
	 * (validateReservationDate(date)) { return flightDate.substring(0, 9); }
	 * else { throw new RuntimeException(); }
	 * 
	 * }
	 */

	public boolean validateflightDate(String flightDate) {
		boolean validateDate = true;
		try {
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ssZ");
			java.util.Date dateformatter = date.parse(flightDate);
		} catch (Exception e) {
			validateDate = false;
		}
		return validateDate;

	}

	public boolean validateBookingNumber(String bookingNumber) {
		if (!bookingNumber.matches("^[0-9]+$")) {
			validatedJson = false;
		}
		return validatedJson;

	}

	public boolean validateFirstName(String firstname) {
		if (!firstname.matches("^[a-zA-Z]+$")) {
			validatedJson = false;
		}
		return validatedJson;

	}

	public boolean validateLastName(String name) {
		if (!name.matches("^[a-zA-Z]+$")) {
			validatedJson = false;
		}
		return validatedJson;

	}

	public boolean validatePassport(String passport) {
		if (passport.isEmpty()) {
			validatedJson = false;
		}
		return validatedJson;

	}

	public boolean validateReservationJson() {
		return validatedJson;

	}
}