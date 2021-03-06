package ca.ulaval.glo4002.flycheckin.boarding.services.seat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4002.flycheckin.boarding.client.AmsMapEncoded;
import ca.ulaval.glo4002.flycheckin.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flycheckin.boarding.domain.seat.Seat;
import ca.ulaval.glo4002.flycheckin.boarding.domain.seat.SeatAssignation;
import ca.ulaval.glo4002.flycheckin.boarding.domain.seat.SeatAssignationRepository;
import ca.ulaval.glo4002.flycheckin.boarding.domain.seat.strategy.SeatAssignationStrategy;
import ca.ulaval.glo4002.flycheckin.boarding.domain.seat.strategy.SeatAssignationStrategyFactory;
import ca.ulaval.glo4002.flycheckin.boarding.exception.BoardingModuleException;
import ca.ulaval.glo4002.flycheckin.boarding.services.external.PlaneModelService;

public class SeatAssignationService {

  private static int assignationNumberProvider = 1;
  private static Map<String, List<Seat>> availableSeatMap = new HashMap<String, List<Seat>>();

  private SeatAssignation seatAssignation;
  private SeatAssignationRepository seatAssignationRepository;
  private SeatAssignationStrategyFactory seatAssignationStrategyFactory;

  public SeatAssignationService(SeatAssignation seatAssignation, SeatAssignationRepository seatAssignationRepository) {
    this.seatAssignation = seatAssignation;
    this.seatAssignationRepository = seatAssignationRepository;
    this.seatAssignationStrategyFactory = new SeatAssignationStrategyFactory();
  }

  public SeatAssignationService(SeatAssignation seatAssignation, SeatAssignationRepository seatAssignationRepository,
      SeatAssignationStrategyFactory seatAssignationStrategyFactory) {
    this.seatAssignation = seatAssignation;
    this.seatAssignationRepository = seatAssignationRepository;
    this.seatAssignationStrategyFactory = seatAssignationStrategyFactory;
  }

  public SeatAssignation assignSeatToPassenger(Passenger passenger, String mode) throws BoardingModuleException {
    SeatAssignationStrategy seatAssignationStrategy = seatAssignationStrategyFactory
        .createSeatAssignationStrategy(mode);
    List<Seat> availableSeats = getAvalaibleSeatsForFlight(passenger.getFlightNumber(), passenger.getFlightDate());

    String seatNumber = seatAssignationStrategy.assignSeatNumber(availableSeats, passenger.getSeatClass(),
        passenger.isChild());
    seatAssignation.createAssignation(seatNumber, passenger.getPassengerHash(), assignationNumberProvider);
    seatAssignationRepository.persistSeatAssignation(seatAssignation);
    makeSeatNumberUnavailable(passenger.getFlightNumber(), passenger.getFlightDate(), seatNumber);

    assignationNumberProvider++;
    return seatAssignation;
  }

  private List<Seat> getAvalaibleSeatsForFlight(String flightNumber, Date flightDate) {
    String flightInfos = flightNumber + flightDate.toString();
    if (!(availableSeatMap.containsKey(flightInfos))) {
      PlaneModelService planeModelService = new PlaneModelService();
      AmsMapEncoded amsMapConnector = new AmsMapEncoded();
      String planeModel = amsMapConnector.getPlaneModelByFlightNumber(flightNumber);
      availableSeatMap.put(flightInfos, planeModelService.getSeatsAccordingPlaneModel(planeModel));
    }
    return availableSeatMap.get(flightInfos);
  }

  private void makeSeatNumberUnavailable(String flightNumber, Date flightDate, String seatNumber) {
    String flightInfos = flightNumber + flightDate.toString();
    List<Seat> availableSeats = availableSeatMap.get(flightInfos);
    for (Seat seat : availableSeats) {
      if (seat.getSeatNumber().equals(seatNumber)) {
        availableSeats.remove(seat);
        break;
      }
    }
    availableSeatMap.replace(flightInfos, availableSeats);
  }
}
