package codoacodo.vuelosapi.services;

import codoacodo.vuelosapi.model.FlightCrew;
import codoacodo.vuelosapi.model.Passenger;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "personas-api")
public interface FlightClient {
    @GetMapping("/flightCrew/list")
    List<FlightCrew> listAll();

    @GetMapping("/passenger/list")
    List<Passenger> listAllPassengers();

    @GetMapping("/flightCrew/findByDni/{dni}")
     FlightCrew findFlightCrewByDni(@PathVariable long dni);

    @GetMapping("/passenger/findByDni/{dni}")
    Passenger findPassengerByDni(@PathVariable (name ="dni")long dni);

    @GetMapping("/flightCrew/findAllById")
    List<FlightCrew> findByAllId(@RequestBody List<Long> crewIds);

    @PutMapping("/flightCrew/update/{dni}")
     FlightCrew updateCrew(@RequestBody FlightCrew flightCrew, @PathVariable(name ="dni") long dni);

    @PutMapping("/flightCrew/updateAll")
    List<FlightCrew> updateAllCrew(@RequestBody List<FlightCrew> flightCrewList );

    @PutMapping("/passenger/updateAll")
    List<Passenger> updateAllPassengers(@RequestBody List<Passenger> passengerList );

    @PutMapping("/passenger/update/{dni}")
    Passenger updatePassenger(@RequestBody Passenger passenger,  @PathVariable(name ="dni") long dni );

}
