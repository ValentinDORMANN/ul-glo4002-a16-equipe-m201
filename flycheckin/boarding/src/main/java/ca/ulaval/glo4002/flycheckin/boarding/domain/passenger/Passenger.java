package ca.ulaval.glo4002.flycheckin.boarding.domain.passenger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.ulaval.glo4002.flycheckin.boarding.domain.luggage.Luggage;
import ca.ulaval.glo4002.flycheckin.boarding.exception.NotAllowableLuggageException;

public abstract class Passenger {

  private static final double BASE_PRICE = 0;
  private static final int CHECKED_LUGGAGE_LIMIT = 3;
  private static final int VIP_CHECKED_LUGGAGE_LIMIT = 4;
  private static final int CARRY_ON_LUGGAGE_LIMIT = 1;
  private static final double VIP_DISCOUNT = 0.95;
  protected static final String CHECKED_LUGGAGE_TYPE = "checked";
  protected static final String CARRY_ON_LUGGAGE_TYPE = "carry-on";
  private String flightNumber;
  private Date flightDate;
  private String passengerHash;
  private String seatClass;
  private boolean isVip;
  private List<Luggage> luggages;

  public Passenger(String flightNumber, Date flightDate, String passengerHash, String seatClass, boolean isVip) {
    this.flightNumber = flightNumber;
    this.flightDate = flightDate;
    this.passengerHash = passengerHash;
    this.seatClass = seatClass;
    this.isVip = isVip;
    luggages = new ArrayList<Luggage>();
  }

  public void addLuggage(Luggage luggage) throws NotAllowableLuggageException {
    verifyLuggageAllowable(luggage);
    double luggagePrice = calculateLuggagePrice(luggage);
    luggage.setPrice(luggagePrice);
    luggages.add(luggage);
  }

  protected abstract double calculateLuggagePrice(Luggage luggage);

  private void verifyLuggageAllowable(Luggage luggage) throws NotAllowableLuggageException {
    verifyLuggageLimitNumberReached(luggage);
    verifyLuggageDimensionAllowable(luggage);
    verifyLuggageWeightAllowable(luggage);
  }

  private void verifyLuggageLimitNumberReached(Luggage luggage) throws NotAllowableLuggageException {
    if (luggage.isType(CARRY_ON_LUGGAGE_TYPE))
      verifyAnotherCarryOnLuggageAllowable();
    else
      verifyAnotherCheckedLuggageAllowable();
  }

  private void verifyAnotherCarryOnLuggageAllowable() {
    if (countTypeLuggageAssigned(CARRY_ON_LUGGAGE_TYPE) == CARRY_ON_LUGGAGE_LIMIT)
      throw new NotAllowableLuggageException();
  }

  private void verifyAnotherCheckedLuggageAllowable() {
    if (!isVip && countTypeLuggageAssigned(CHECKED_LUGGAGE_TYPE) == CHECKED_LUGGAGE_LIMIT)
      throw new NotAllowableLuggageException();
    else if (isVip && countTypeLuggageAssigned(CHECKED_LUGGAGE_TYPE) == VIP_CHECKED_LUGGAGE_LIMIT)
      throw new NotAllowableLuggageException();
  }

  private void verifyAnotherCarryOnLuggageAllowable2(Luggage luggage) {
    luggage.checkLuggageAllowable(countTypeLuggageAssigned(CARRY_ON_LUGGAGE_TYPE), isVip);
  }

  private void verifyAnotherCheckedLuggageAllowable2(Luggage luggage) {
    luggage.checkLuggageAllowable(countTypeLuggageAssigned(CHECKED_LUGGAGE_TYPE), isVip);
    ;
  }

  protected abstract void verifyLuggageDimensionAllowable(Luggage luggage);

  protected abstract void verifyLuggageWeightAllowable(Luggage luggage);

  protected int countTypeLuggageAssigned(String type) {
    int typeLuggageNumber = 0;
    for (int i = 0; i < luggages.size(); i++) {
      if (luggages.get(i).isType(type))
        typeLuggageNumber++;
    }
    return typeLuggageNumber;
  }

  public double getTotalPrice() {
    double totalPrice = BASE_PRICE;
    for (Luggage luggage : luggages)
      totalPrice += luggage.getPrice();
    if (isVip)
      return appliedVipDiscount(totalPrice);
    return totalPrice;
  }

  private double appliedVipDiscount(double price) {
    return price * VIP_DISCOUNT;
  }

  public String getPassengerHash() {
    return this.passengerHash;
  }

  public String getFlightNumber() {
    return this.flightNumber;
  }

  public Date getFlightDate() {
    return this.flightDate;
  }

  public String getSeatClass() {
    return this.seatClass;
  }

  public boolean getIsVip() {
    return this.isVip;
  }

  public List<Luggage> getLuggages() {
    return this.luggages;
  }
}