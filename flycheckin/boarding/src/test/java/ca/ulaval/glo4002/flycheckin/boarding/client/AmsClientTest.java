package ca.ulaval.glo4002.flycheckin.boarding.client;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AmsClientTest {

  private static final String BOEING = "boeing-777-300";
  private static final String FLIGHT_BOEING = "QK-432";
  private static final String DASH = "dash-8";
  private static final String FLIGHT_DASH = "QK-918";
  private static final String AIRBUS = "a320";
  private static final String FLIGHT_AIRBUS = "NK-750";
  private AmsClient amsClient;

  @Before
  public void initiateTest() {
    amsClient = new AmsClient();
  }

  @Test
  public void givenFlightBoeingWhenGetModelThenReturnBoeing777300() {
    String boeingModel = amsClient.getPlaneModelByFlightNumber(FLIGHT_BOEING);
    assertEquals(BOEING, boeingModel);
  }

  @Test
  public void givenFlightAirbusWhenGetModelThenReturnA320() {
    String airbusModel = amsClient.getPlaneModelByFlightNumber(FLIGHT_AIRBUS);
    assertEquals(AIRBUS, airbusModel);
  }

  @Test
  public void givenFlightDashWhenGetModelThenReturnDash8() {
    String dashModel = amsClient.getPlaneModelByFlightNumber(FLIGHT_DASH);
    assertEquals(DASH, dashModel);
  }

}