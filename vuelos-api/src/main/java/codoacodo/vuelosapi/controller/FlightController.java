package codoacodo.vuelosapi.controller;

import codoacodo.vuelosapi.model.*;
import codoacodo.vuelosapi.services.FlightClient;
import codoacodo.vuelosapi.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flights")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightClient flightClient;

    @GetMapping("/list")
    public List<Flight> getAllFlights(){
        return flightService.getAllFlights();
    }

    @GetMapping("/findByCompany/{companyId}")
    public Optional<List<FlightDTO>> findByCompany(@PathVariable (name = "companyId") long companyId){
       return flightService.findByCompany(companyId);
    }

    @GetMapping("{id}")
    public Optional<FlightDTO> findById(@PathVariable(name = "id") Long id){
        return flightService.findById(id);
    }

    @GetMapping("/less/{price}")
    public List<FlightDTO> getLessThan(@PathVariable(name = "price") double price){
        return flightService.getLessThan(price);
    }

    @GetMapping("/origin")
    public Optional<List<FlightDTO>> getByOrigin(@RequestParam String origin){
        return flightService.getByOrigin(origin);
    }

    @GetMapping("/destination")
    public Optional<List<FlightDTO>> getByDestination(@RequestParam String destination){
        return flightService.getByDestination(destination);
    }

    @GetMapping("/locations")
    public Optional<List<FlightDTO>> getFlightsByLocations(@RequestParam String origin, @RequestParam String destination) {
        return flightService.getByOriginAndDestination(origin, destination);
    }

    @GetMapping("/dolares")
    public List<Dolar> getDolares(){
        return flightService.getDolares();
    }

    @GetMapping("fetchAllDolars")
    public Dolar[] fetchAllDolars (){

        return flightService.fetchAllDolars();
    }
    @GetMapping("/dolar/{casa}")
    public Dolar getDolarCasa(@PathVariable(name = "casa") String casa){
        return flightService.getDolarCasa(casa);
    }

    @GetMapping("/dolar")
    public double getDolar(){
        return flightService.getDolarTarjeta();
    }

    @GetMapping("/flightCrew/list")
    public List<FlightCrew> listAll(){
        return flightClient.listAll();
    }

    @PostMapping("/add")
    public Flight addFlight (@RequestBody Flight flight){
        return flightService.addFlight(flight);
    }

    @PostMapping("/addList")
    public List<Flight> addFlightList (@RequestBody List<Flight> flights){
        return flightService.addFlightList(flights);
    }

    @PostMapping("/addFlightsToCompany")
    public Optional<List<FlightDTO>> addFlightsToCompany(@RequestBody List<Flight> flights, @RequestParam long companyId){
        return flightService.addFlightsToCompany(flights,companyId);
    }

    @PostMapping("/addFlightToCompany")
    public Optional<FlightDTO> addFlightToCompany(@RequestBody Flight flight, @RequestParam long companyId){
        return flightService.addFlightToCompany(flight,companyId);
    }
    // este se le pasa solo los id de los vuelos que queremos asociar con esa compania, facilita la carga
    @PostMapping("/addToCompany")
    public Optional<List<FlightDTO>> addToCompany(@RequestBody List<Long> flightsId, @RequestParam long companyId){
        return flightService.addToCompany(flightsId, companyId);
    }

    @PostMapping("/addCrew")
    public FlightDTO addCrew(@RequestBody List<Long> flightCrew, @RequestParam long flightId){
        return flightService.addCrew(flightCrew, flightId);
    }

    @PostMapping("/addPassengers")
    public FlightDTO addPassengers(@RequestBody List<Long> passengersId, @RequestParam long flightId){
        return flightService.addPassengers(passengersId, flightId);
    }

    @PutMapping("/update/{id}")
    public Optional<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flight){
        return flightService.updateFlight(id,flight);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFlight(@PathVariable Long id){
       flightService.deleteFlight(id);
    }

}
