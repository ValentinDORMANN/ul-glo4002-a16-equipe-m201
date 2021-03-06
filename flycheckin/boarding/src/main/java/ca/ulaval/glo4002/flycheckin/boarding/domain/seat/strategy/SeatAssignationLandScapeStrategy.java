package ca.ulaval.glo4002.flycheckin.boarding.domain.seat.strategy;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.flycheckin.boarding.domain.seat.NoSeatAvailableException;
import ca.ulaval.glo4002.flycheckin.boarding.domain.seat.Seat;

public class SeatAssignationLandScapeStrategy extends SeatAssignationStrategy {

  @Override
  public String assignSeatNumber(List<Seat> availableSeats, String seatClass, boolean isChild) throws NoSeatAvailableException {
    availableSeats = siftAvailableSeatsBySeatClass(availableSeats, seatClass);
    availableSeats = siftAvailableSeatAccordingPassengerAge(availableSeats, isChild);

    Seat selectedSeat = getSeatWithBestView(availableSeats);
    return selectedSeat.getSeatNumber();
  }

  private Seat getSeatWithBestView(List<Seat> availableSeats) {
    availableSeats = filterAvailableSeatsByLandScape(availableSeats);
    Seat selectedSeat = selectCheapestSeat(availableSeats);
    return selectedSeat;
  }

  private List<Seat> filterAvailableSeatsByLandScape(List<Seat> availableSeats) {
    availableSeats = filterAvailableSeatsWithWindowView(availableSeats);
    availableSeats = filterAvailableSeatsWithClearView(availableSeats);
    return availableSeats;
  }

  private List<Seat> filterAvailableSeatsWithClearView(List<Seat> availableSeats) {
    List<Seat> filteredSeatList = new ArrayList<Seat>();
    for (Seat seat : availableSeats) {
      if (seat.isClearView())
        filteredSeatList.add(seat);
    }
    if (filteredSeatList.isEmpty())
      return availableSeats;
    return filteredSeatList;
  }

  private List<Seat> filterAvailableSeatsWithWindowView(List<Seat> availableSeats) {
    List<Seat> filteredSeatList = new ArrayList<Seat>();
    for (Seat seat : availableSeats) {
      if (seat.isNearWindow())
        filteredSeatList.add(seat);
    }
    if (filteredSeatList.isEmpty())
      return availableSeats;
    return filteredSeatList;
  }
}
