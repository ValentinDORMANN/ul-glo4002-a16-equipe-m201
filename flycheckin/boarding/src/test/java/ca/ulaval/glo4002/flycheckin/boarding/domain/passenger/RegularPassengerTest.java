package ca.ulaval.glo4002.flycheckin.boarding.domain.passenger;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.flycheckin.boarding.domain.luggage.Luggage;
import ca.ulaval.glo4002.flycheckin.boarding.domain.luggage.NotAllowableLuggageException;

public class RegularPassengerTest {

  private static final double CARRY_ON_LUGGAGE_PRICE = 30.0;
  private static final double CARRY_ON_LUGGAGE_DISCOUNT_PRICE = 28.50;

  private static final String CHECKED_LUGGAGE_CATEGORY = "checked";
  private static final String CARRY_ON_LUGGAGE_CATEGORY = "standard";

  private static final String FLIGHT_NUMBER = "AAAA";
  private static final Date FLIGHT_DATE = new Date();
  private static final String HASH = "hash";
  private static final String ECONOMY = "economy";
  private static final boolean IS_VIP = true;
  private static final boolean IS_CHILD = true;
  private static final double CHECKED_LUGGAGE_WEIGHT_LIMIT = 23;

  private static final double DELTA = 0.001;

  private Luggage luggageMock;

  private RegularPassenger regularPassenger;

  @Before
  public void initiateTest() {
    luggageMock = mock(Luggage.class);

    regularPassenger = new RegularPassenger(FLIGHT_NUMBER, FLIGHT_DATE, HASH, ECONOMY, !IS_VIP, !IS_CHILD);
  }

  @Test(expected = NotAllowableLuggageException.class)
  public void givenCarryOnLuggageAssignedWhenAddAnotherCarryOnLuggageThenThrowException() {
    givenStandardCarryOnLuggage(luggageMock);
    regularPassenger.addLuggage(luggageMock);

    regularPassenger.addLuggage(luggageMock);
  }

  @Test(expected = NotAllowableLuggageException.class)
  public void givenUnusualSizeCarryOnLuggageWhenAddLuggageToPassengerThenThrowException() {
    givenStandardCarryOnLuggage(luggageMock);
    doThrow(new NotAllowableLuggageException()).when(luggageMock).verifyAllowableDimension();

    regularPassenger.addLuggage(luggageMock);
  }

  @Test
  public void givenStandardCarryOnLuggageWhenAddLuggageToPassengerThenVerifyLuggageDimension() {
    givenStandardCarryOnLuggage(luggageMock);

    regularPassenger.addLuggage(luggageMock);

    verify(luggageMock).verifyAllowableDimension();
  }

  @Test(expected = NotAllowableLuggageException.class)
  public void givenUnusualWeightCarryOnLuggageWhenAddLuggageToPassengerThenThrowException() {
    givenStandardCarryOnLuggage(luggageMock);
    doThrow(new NotAllowableLuggageException()).when(luggageMock).verifyAllowableWeight(CHECKED_LUGGAGE_WEIGHT_LIMIT);

    regularPassenger.addLuggage(luggageMock);
  }

  @Test
  public void givenStandardCarryOnLuggageWhenAddLuggageToPassengerThenVerifyLuggageWeight() {
    givenStandardCarryOnLuggage(luggageMock);

    regularPassenger.addLuggage(luggageMock);

    verify(luggageMock).verifyAllowableWeight(CHECKED_LUGGAGE_WEIGHT_LIMIT);
  }

  @Test
  public void givenStandardCarryOnLuggageWhenAddLuggageToPassengerThenCalculateItsPrice() {
    givenStandardCarryOnLuggage(luggageMock);

    regularPassenger.addLuggage(luggageMock);

    verify(luggageMock).calculatePrice();
  }

  @Test(expected = NotAllowableLuggageException.class)
  public void givenPassengerWithAllowableCheckedLuggagesAssignedWhenAddAnotherCheckedLuggageThenThrowException() {
    givenStandardCheckedLuggage(luggageMock);
    regularPassenger.addLuggage(luggageMock);
    regularPassenger.addLuggage(luggageMock);
    regularPassenger.addLuggage(luggageMock);

    regularPassenger.addLuggage(luggageMock);
  }

  @Test(expected = NotAllowableLuggageException.class)
  public void givenVipPassengerWithAllowableCheckedLuggagesAssignedWhenAddAnotherCheckedLuggageThenThrowException() {
    regularPassenger = new RegularPassenger(FLIGHT_NUMBER, FLIGHT_DATE, HASH, ECONOMY, IS_VIP, !IS_CHILD);
    givenStandardCheckedLuggage(luggageMock);

    regularPassenger.addLuggage(luggageMock);
    regularPassenger.addLuggage(luggageMock);
    regularPassenger.addLuggage(luggageMock);
    regularPassenger.addLuggage(luggageMock);

    regularPassenger.addLuggage(luggageMock);
  }

  @Test(expected = NotAllowableLuggageException.class)
  public void givenUnusualDimensionCheckedLuggageWhenAddLuggageToPassengerThenThrowException() {
    givenStandardCheckedLuggage(luggageMock);
    doThrow(new NotAllowableLuggageException()).when(luggageMock).verifyAllowableDimension();

    regularPassenger.addLuggage(luggageMock);
  }

  @Test
  public void givenUnusualDimensionCheckedLuggageWhenAddLuggageToPassengerThenVerifyAllowableDimension() {
    givenStandardCheckedLuggage(luggageMock);

    regularPassenger.addLuggage(luggageMock);

    verify(luggageMock).verifyAllowableDimension();
  }

  @Test(expected = NotAllowableLuggageException.class)
  public void givenUnusualWeightCheckedLuggageWhenAddLuggageToPassengerThenThrowException() {
    givenStandardCheckedLuggage(luggageMock);
    doThrow(new NotAllowableLuggageException()).when(luggageMock).verifyAllowableWeight(CHECKED_LUGGAGE_WEIGHT_LIMIT);

    regularPassenger.addLuggage(luggageMock);
  }

  @Test
  public void givenStandardCheckedLuggageWhenAddLuggageToPassengerThenVerifyAllowableWeight() {
    givenStandardCheckedLuggage(luggageMock);

    regularPassenger.addLuggage(luggageMock);

    verify(luggageMock).verifyAllowableWeight(CHECKED_LUGGAGE_WEIGHT_LIMIT);
  }

  @Test
  public void givenStandardCheckedLuggageWhenAddLuggageToPassengerThenVerifyItAssignFree() {
    givenStandardCheckedLuggage(luggageMock);

    regularPassenger.addLuggage(luggageMock);

    verify(luggageMock).assignLuggageFree();
  }

  @Test
  public void givenThreeCheckedLuggageWhenAddLuggageThenVerifyItCalculatesPriceThreetimes() {
    givenStandardCheckedLuggage(luggageMock);

    regularPassenger.addLuggage(luggageMock);
    regularPassenger.addLuggage(luggageMock);
    regularPassenger.addLuggage(luggageMock);

    verify(luggageMock, times(3)).calculatePrice();
  }

  @Test
  public void givenFreeCheckedLuggageAssignedWhenAddAnotherCheckedLuggageThenVerifyItNotAssignFree() {
    givenStandardCheckedLuggage(luggageMock);
    regularPassenger.addLuggage(luggageMock);

    regularPassenger.addLuggage(luggageMock);

    verify(luggageMock, times(1)).assignLuggageFree();
  }

  @Test
  public void givenStandardCarryOnLuggageAssignedWhenGetTotalPriceThenReturnLuggagePrice() {
    givenStandardCarryOnLuggage(luggageMock);

    regularPassenger.addLuggage(luggageMock);
    double price = regularPassenger.getTotalPrice();

    assertEquals(CARRY_ON_LUGGAGE_PRICE, price, DELTA);
  }

  @Test
  public void givenCarryOnLuggageToVipPassengerWhenGetTotalPriceThenApplyDiscount() {
    regularPassenger = new RegularPassenger(FLIGHT_NUMBER, FLIGHT_DATE, HASH, ECONOMY, IS_VIP, !IS_CHILD);
    givenStandardCarryOnLuggage(luggageMock);

    regularPassenger.addLuggage(luggageMock);
    double price = regularPassenger.getTotalPrice();

    assertEquals(CARRY_ON_LUGGAGE_DISCOUNT_PRICE, price, DELTA);
  }

  private void givenStandardCarryOnLuggage(Luggage luggageMock) {
    willReturn(CARRY_ON_LUGGAGE_CATEGORY).given(luggageMock).getCategory();
    willReturn(true).given(luggageMock).hasSameCategory(luggageMock);
    willReturn(CARRY_ON_LUGGAGE_PRICE).given(luggageMock).getPrice();
  }

  private void givenStandardCheckedLuggage(Luggage luggageMock) {
    willReturn(CHECKED_LUGGAGE_CATEGORY).given(luggageMock).getCategory();
    willReturn(true).given(luggageMock).hasSameCategory(luggageMock);
    willReturn(true).given(luggageMock).isFree();
  }
}
