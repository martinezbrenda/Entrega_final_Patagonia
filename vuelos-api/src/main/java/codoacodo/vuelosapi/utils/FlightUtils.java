package codoacodo.vuelosapi.utils;
import codoacodo.vuelosapi.model.Flight;
import codoacodo.vuelosapi.model.FlightCrew;
import codoacodo.vuelosapi.model.FlightDTO;
import codoacodo.vuelosapi.model.Passenger;
import codoacodo.vuelosapi.services.FlightClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightUtils {
    @Autowired
    private FlightClient flightClient;

    public List<Flight> detectOffers(List<Flight> flightList, double price){
        return flightList.stream().filter(flight -> flight.getPrice() < price).collect(Collectors.toList());
    }

    public FlightDTO flightMapper(Flight flight, double price){
        List<Long> crewIds = flight.getAssignedCrew();
        List<String> crewNames = new ArrayList<>();
        if(crewIds != null){
            for (Long id : crewIds){
                FlightCrew flightCrew = flightClient.findFlightCrewByDni(id);
                crewNames.add("-" +flightCrew.getRole() +" : " + flightCrew.getName());
            }
        }else{
            crewNames.add("Not assigned yet");
        }
        List<Long> passengersIds = flight.getAssignedPassengers();
        List<String> passengerWitSeatNumber = new ArrayList<>();
        if(passengersIds != null){
            for (Long id : passengersIds) {
                Passenger passenger = flightClient.findPassengerByDni(id);
                passengerWitSeatNumber.add("-" + passenger.getName() + " |Seat: " + passenger.getSeatNumber()+"|");
            }
        }else{
            passengerWitSeatNumber.add("Not assigned yet");
        }
        return new FlightDTO(flight.getId(), flight.getOrigin(), flight.getDestination(), flight.getDepartureDate(),
                flight.getArrivalDate(), flight.getPrice() * price, flight.getFrequency(), flight.getCompany(),crewNames, passengerWitSeatNumber);
    }

    public List<FlightDTO> flightListMapper(List<Flight> flightList, double price){
        List<FlightDTO> flightDTOList = new ArrayList<>();
        for(Flight flight : flightList){
            List<Long> crewIds = flight.getAssignedCrew();
            List<String> crewNames = new ArrayList<>();
            if(crewIds != null){
            for (Long id : crewIds){
                FlightCrew flightCrew = flightClient.findFlightCrewByDni(id);
                crewNames.add("-" +flightCrew.getRole() +" : " + flightCrew.getName());
            }
            }else{
                crewNames.add("Not assigned yet");
            }
            List<Long> passengersIds = flight.getAssignedPassengers();
            List<String> passengerWitSeatNumber = new ArrayList<>();


            if(passengersIds != null){
            for (Long id : passengersIds) {
                Passenger passenger = flightClient.findPassengerByDni(id);

                passengerWitSeatNumber.add("-" +passenger.getName() + " |Seat: " + passenger.getSeatNumber()+"|");
            }
            }else{
                passengerWitSeatNumber.add("Not assigned yet");
            }
            flightDTOList.add(new FlightDTO(flight.getId(), flight.getOrigin(), flight.getDestination(),
                    flight.getDepartureDate(), flight.getArrivalDate(),
                    flight.getPrice() * price, flight.getFrequency(), flight.getCompany(),crewNames, passengerWitSeatNumber));
        }
        return flightDTOList;
    }
}
