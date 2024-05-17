package codoacodo.vuelosapi.services;

import codoacodo.vuelosapi.model.FlightCrew;
import codoacodo.vuelosapi.model.Passenger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "personas-api")
public interface FlightClient {
    @GetMapping("/flightCrew/list")
    List<FlightCrew> listAll();

    @GetMapping("/passenger/list")
    List<Passenger> listAllPassengers();

    @GetMapping("/flightCrew/findByIdBody")
     FlightCrew findById(@RequestBody long dni);

    @GetMapping("/flightCrew/findAllById")
    List<FlightCrew> findByAllId(@RequestBody List<Long> crewIds);

    @PutMapping("flightCrew/update/{dni}")
    FlightCrew update (@RequestBody FlightCrew flightCrew, @PathVariable(name ="dni") long dni);

    @PutMapping("flightCrew/updateAll")
     List<FlightCrew> updateAll(@RequestBody List<FlightCrew> flightCrewList );

    @PutMapping("flightCrew/updateAll")
    List<Passenger> updateAllPassengers(@RequestBody List<Passenger> passengerList );

}
