package codoacodo.vuelosapi.utils;

import codoacodo.vuelosapi.model.Flight;
import codoacodo.vuelosapi.model.FlightCrew;
import codoacodo.vuelosapi.model.FlightDTO;
import codoacodo.vuelosapi.services.FlightClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FlightUtils {
    @Autowired
    FlightClient flightClient;

    public List<Flight> detectOffers(List<Flight> flightList, double price){
        return flightList.stream().filter(flight -> flight.getPrice() < price).collect(Collectors.toList());
    }

    public FlightDTO flightMapper(Flight flight, double price){
        /*List<Long> crewIds = flight.getAssignedCrew();
        List<String> crewNames = new ArrayList<>();
        for (Long id : crewIds){
            FlightCrew flightCrew = flightClient.findById(id);
            crewNames.add(flightCrew.getName());
        }*/
        return new FlightDTO(flight.getId(), flight.getOrigin(), flight.getDestination(), flight.getDepartureDate(),
                flight.getArrivalDate(), flight.getPrice() * price, flight.getFrequency(), flight.getCompany(), flight.getAssignedCrew(),flight.getAssignedPassengers());
    }

    public List<FlightDTO> flightListMapper(List<Flight> flightList, double price){
        List<FlightDTO> flightDTOList = new ArrayList<>();
        for(Flight flight : flightList){
            /*List<Long> crewIds = flight.getAssignedCrew();
            List<String> crewNames = new ArrayList<>();
            for (Long id : crewIds){
                FlightCrew flightCrew = flightClient.findById(id);
                crewNames.add(flightCrew.getName());
            }*/
            flightDTOList.add(new FlightDTO(flight.getId(), flight.getOrigin(), flight.getDestination(),
                    flight.getDepartureDate(), flight.getArrivalDate(),
                    flight.getPrice() * price, flight.getFrequency(), flight.getCompany(), flight.getAssignedCrew(),flight.getAssignedPassengers()));
        }
        return flightDTOList;
    }
}
