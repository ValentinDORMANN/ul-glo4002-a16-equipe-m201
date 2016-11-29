package ca.ulaval.glo4002.flycheckin.boarding.persistence;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.flycheckin.boarding.domain.seat.SeatAssignation;
import ca.ulaval.glo4002.flycheckin.boarding.domain.seat.SeatAssignationRepository;
import ca.ulaval.glo4002.flycheckin.boarding.exception.AssignationNumberUsedException;
import ca.ulaval.glo4002.flycheckin.boarding.exception.SeatAlreadyAssignedException;

public class SeatAssignationPersistence implements SeatAssignationRepository {

  private static final String STRING_EMPTY = "";
  private static final String ERROR_SEAT_UNASSIGNED = "Error: This passenger seat is already assigned.";
  private static final String ERROR_ASSIGNATION_NUMBER_USED = "Error: This assignation number is already used.";
  private static Map<Integer, SeatAssignation> seatAssignationMap = new HashMap<Integer, SeatAssignation>();

  @Override
  public void persistSeatAssignation(SeatAssignation seatAssignation) {
    if (seatAssignationMap.containsKey(seatAssignation.getAssignationNumber()))
      throw new AssignationNumberUsedException(ERROR_ASSIGNATION_NUMBER_USED);
    else if (!getPassengerHashSeatNumber(seatAssignation.getPassengerHash()).isEmpty())
      throw new SeatAlreadyAssignedException(ERROR_SEAT_UNASSIGNED);
    seatAssignationMap.put(seatAssignation.getAssignationNumber(), seatAssignation);
  }

  @Override
  public String getPassengerHashSeatNumber(String passengerHash) {
    String seatNumber = STRING_EMPTY;
    for (SeatAssignation seatAssignation : seatAssignationMap.values()) {
      if (seatAssignation.isAssociateToThisHash(passengerHash))
        seatNumber = seatAssignation.getSeatNumber();
    }
    return seatNumber;
  }

  public void clearSeatAssignationMap() {
    seatAssignationMap.clear();
  }
}