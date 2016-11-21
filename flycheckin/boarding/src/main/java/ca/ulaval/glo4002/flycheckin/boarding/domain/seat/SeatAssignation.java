package ca.ulaval.glo4002.flycheckin.boarding.domain.seat;

public class SeatAssignation {

  private int assignationNumber;
  private String passengerHash;
  private String seatNumber;

  public SeatAssignation() {
  }

  public void createAssignation(String seatNumber, String passengerHash, int assignationNumber) {
    this.passengerHash = passengerHash;
    this.seatNumber = seatNumber;
    this.assignationNumber = assignationNumber;
  }

  public boolean isAssociateToThisHash(String passengerHash) {
    return this.passengerHash.equals(passengerHash);
  }

  public int getAssignationNumber() {
    return this.assignationNumber;
  }

  public String getPassengerHash() {
    return this.passengerHash;
  }

  public String getSeatNumber() {
    return this.seatNumber;
  }
}