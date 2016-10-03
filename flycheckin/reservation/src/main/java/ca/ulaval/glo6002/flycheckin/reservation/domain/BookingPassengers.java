package ca.ulaval.glo6002.flycheckin.reservation.domain;

public class BookingPassengers {

	private String firstName;
	private String lastName;
	private int age;
	private String passportNumber;
	private String seatClass;
	private String flightNumber;

	public BookingPassengers(String firstName, String lastName, int age, String passportNumber, String seatClass,
			String flightNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.passportNumber = passportNumber;
		this.seatClass = seatClass;
		this.flightNumber = flightNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public String getSeatClass() {
		return seatClass;
	}

	public String getFlightNumber() {
		return this.flightNumber;
	}

	public String generateHash() {
		return this.passportNumber + ":" + this.flightNumber;
	}

}
