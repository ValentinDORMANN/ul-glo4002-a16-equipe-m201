package ca.ulaval.glo4002.flycheckin.reservation.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ca.ulaval.glo4002.flycheckin.reservation.domain.Passenger;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PassengerDto {

  public String first_name;
  public String last_name;
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public int age;
  public String passport_number;
  public String seat_class;
  public boolean child;
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public boolean isVip;
  public String passenger_hash;

  public PassengerDto() {
  }

  public PassengerDto(Passenger passenger) {
    this.first_name = passenger.getFirstName();
    this.last_name = passenger.getLastName();
    this.passport_number = passenger.getPassportNumber();
    this.seat_class = passenger.getSeatClass();
    this.child = passenger.isChild();
    this.passenger_hash = passenger.getPassengerHash();
    this.isVip = passenger.getIsVip();
  }
}
