package codoacodo.vuelosapi.services;

import codoacodo.vuelosapi.FlightException.FlightException;
import codoacodo.vuelosapi.configuration.FlightConfiguration;
import codoacodo.vuelosapi.model.*;
import codoacodo.vuelosapi.repository.CompanyRepository;
import codoacodo.vuelosapi.repository.FlightRepository;
import codoacodo.vuelosapi.utils.FlightUtils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;
    @Autowired
    FlightUtils flightUtils;
    @Autowired
    FlightConfiguration flightConfiguration;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    private FlightClient flightClient;

    private static String GET_ALL_FLIGHT_CREW = "http://localhost:8181/flightCrew/list";
    private static String UPDATE_ALL_FLIGHT_CREW = "http://localhost:8181/flightCrew/updateAll";

    static RestTemplate restTemplate = new RestTemplate();

    public List<Flight> getAllFlights() {

        return flightRepository.findAll();
    }
    public List<FlightDTO> flights() {
        return flightUtils.flightListMapper(flightRepository.findAll(),getDolarTarjeta());
    }

    public Optional<List<FlightDTO>> findByCompany(long companyId) {
            List<Flight> flightList = flightRepository.findAll().stream()
                    .filter(flight -> flight.getCompany().getId() == companyId)
                    .collect(Collectors.toList());
            if (flightList.isEmpty()) {
                throw new FlightException("No existen vuelos asociados al Company Id: " + companyId);

            } else {
                return Optional.of(flightUtils.flightListMapper(flightList, getDolarTarjeta()));
            }
    }

    public Optional<FlightDTO> findById (Long id){
        Flight existingFlight = flightRepository.findById(id).orElse(null);
        if(existingFlight != null)
            return Optional.of(flightUtils.flightMapper(existingFlight, getDolarTarjeta()));
        else
            throw new FlightException("El vuelo con ID " + id + " no existe");

    }
    public List<FlightDTO> getLessThan(double price){
        /*flightRepository.findAll().stream().map(flight -> flightUtils.flightMapper(flight,getDolarTarjeta())).collect(Collectors.toList())*/
        /*Supongo que el precio recibido es en pesos*/
        double priceInDolars = ( price / getDolarTarjeta());
        /* Obtengo el precio en dolares y busco por como estan guardados en la base de datos*/
        List<Flight> flightList = flightUtils.detectOffers(flightRepository.findAll(),priceInDolars);
        /* convierto la lista de ofertas en dolares a lista de ofertas en pesos*/
        return flightUtils.flightListMapper(flightList,getDolarTarjeta());
    }

    public Optional<List<FlightDTO>> getByOrigin(String origin){
        List<Flight> flightList = flightRepository.findByOrigin(origin);
        if (flightList.isEmpty()){
            throw new FlightException("No existen vuelos con origen igual a " + origin);
        }
       return Optional.of(flightUtils.flightListMapper(flightList, getDolarTarjeta()));
    }

    public Optional<List<FlightDTO>> getByDestination(String destination){
        List<Flight> flightList = flightRepository.findByDestination(destination);
        if (flightList.isEmpty()){
            throw new FlightException("No existen vuelos con destino igual a " + destination);
        }
        return Optional.of(flightUtils.flightListMapper(flightList, getDolarTarjeta()));
    }

    public Optional<List<FlightDTO>> getByOriginAndDestination(String origin, String destination) {
        List<Flight> flightList = flightRepository.findByOriginAndDestination(origin, destination);
        if (flightList.isEmpty()){
            throw new FlightException("No existen vuelos con origen igual a " + origin + " y destino igual a " + destination);
        }
        return Optional.of(flightUtils.flightListMapper(flightList, getDolarTarjeta()));
    }

    public List <Dolar> getDolares(){
        return flightConfiguration.fetchDolares();
    }

    public Dolar[] fetchAllDolars(){
        return flightConfiguration.fetchAllDolars();
    }

    public Dolar getDolarCasa(String casa){
        return flightConfiguration.fetchDolarCasa(casa);
    }

    public double getDolarTarjeta(){
        return flightConfiguration.fetchDolarCasa("tarjeta").getPromedio();
    }

    public Flight addFlight(Flight flight){

        return flightRepository.save(flight);
    }

    public List<Flight> addFlightList(List<Flight> flights){

        return flightRepository.saveAll(flights);
    }

    public Optional<List<FlightDTO>> addFlightsToCompany(List<Flight> flightList, long companyId){
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty()){
                throw new FlightException("No existe la Company con id: " + companyId);
        }
        Company existinCompany = company.get();
        flightList.forEach(flight -> flight.setCompany(existinCompany));
        flightRepository.saveAll(flightList);
        return Optional.of(flightUtils.flightListMapper(flightList, getDolarTarjeta()));
    }

    public Optional<FlightDTO> addFlightToCompany(Flight flight, long companyId){
        Optional<Company> company = companyRepository.findById(companyId);
        if(company.isEmpty())
            throw new FlightException("No existe la Company con id: " + companyId);
        Company existingCompany = company.get();
        flight.setCompany(existingCompany);
        flightRepository.save(flight);
        return Optional.of(flightUtils.flightMapper(flight, getDolarTarjeta()));
    }
    public Optional<List<FlightDTO>> addToCompany(List<Long> flightsId, long companyId) {
        List<Flight> flightList = flightRepository.findAllById(flightsId);
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty()){
            throw new FlightException("No existe la Company con id: " + companyId);
        }
        if(flightList.isEmpty()){
            throw new FlightException("No existen vuelos con id: " + flightsId);
        }
        Company existinCompany = company.get();
        flightList.forEach(flight -> flight.setCompany(existinCompany));
        flightRepository.saveAll(flightList);
        return Optional.of(flightUtils.flightListMapper(flightList, getDolarTarjeta()));
    }

    /*public FlightDTO addCrew(List<Long> flightCrewIds, long flightId) {
        //validar el flightID
        Optional<Flight> flight = flightRepository.findById(flightId);
        if(flight.isEmpty())
            throw new FlightException("No existe el vuelo con id: "+ flightId);

        //validar los dni de los tripulantes
        FlightCrew[] flightCrewList = restTemplate.getForObject(GET_ALL_FLIGHT_CREW,FlightCrew[].class);
        for (Long dni : flightCrewIds) {
            boolean found = false;
            for (FlightCrew crewMember : flightCrewList) {
                if (crewMember.getDni().equals(dni)) {
                    found = true;
                    //asignar el vuelo a los tripulantes
                    List<Long> assignedFlights = crewMember.getAssignedFlights();
                    assignedFlights.add(flightId);
                    crewMember.setAssignedFlights(assignedFlights);
                    break;
                }
            }
            if (!found) {
                throw new FlightException("No existen Crew members con esos dnis");
            }
        }
        // guardar la actualizacion de los tripulantes
        ResponseEntity<FlightCrew> response = restTemplate.postForEntity(UPDATE_ALL_FLIGHT_CREW,flightCrewList,FlightCrew.class);
        System.out.println("\nRESPUESTA DEL UPDATE\n");
        System.out.println(response);
        //asignar los tripulantes al vuelo
        Flight existingFlight = flight.get();
        List<Long> assignedCrew = existingFlight.getAssignedCrew();
        if(assignedCrew == null){
            existingFlight.setAssignedCrew(flightCrewIds);
        }else{
            assignedCrew.addAll(flightCrewIds);
            existingFlight.setAssignedCrew(assignedCrew);
        }
        flightRepository.save(existingFlight);
        return flightUtils.flightMapper(existingFlight, getDolarTarjeta());
    }*/
    public FlightDTO addCrew(List<Long> flightCrewIds, long flightId){
        //validar el flightId
        Optional<Flight> flight = flightRepository.findById(flightId);
        if(flight.isEmpty())
            throw new FlightException("No existe el vuelo con id: "+ flightId);
        //validar los dnis del crew
        List<FlightCrew> flightCrewList = flightClient.listAll();
        for (Long dni : flightCrewIds) {
            boolean found = false;
            for (FlightCrew crewMember : flightCrewList) {
                if (crewMember.getDni().equals(dni)) {
                    found = true;
                    //asignar el vuelo a los tripulantes
                    List<Long> assignedFlights = crewMember.getAssignedFlights();
                    assignedFlights.add(flightId);
                    crewMember.setAssignedFlights(assignedFlights);
                    break;
                }
            }
            if (!found) {
                throw new FlightException("No existen Crew members con esos dnis");
            }
        }
        // asigno el vuelo a los tripulantes
        flightClient.updateAll(flightCrewList);
        //asigno los tripulantes al vuelo

        Flight existingFlight = flight.get();
        List<Long> assignedCrew = existingFlight.getAssignedCrew();
        if(assignedCrew == null){
            existingFlight.setAssignedCrew(flightCrewIds);
        }else{
            assignedCrew.addAll(flightCrewIds);
            existingFlight.setAssignedCrew(assignedCrew);
        }
        flightRepository.save(existingFlight);
        return flightUtils.flightMapper(existingFlight, getDolarTarjeta());

    }
    public FlightDTO addPassengers(List<Long> passengersId, long flightId) {
        //validar el flightId
        Optional<Flight> flight = flightRepository.findById(flightId);
        if(flight.isEmpty())
            throw new FlightException("No existe el vuelo con id: "+ flightId);
        //validar los dnis
        List<Passenger> passengerList = flightClient.listAllPassengers();
        if(passengerList.isEmpty())
            throw new FlightException("No existen pasajeros con los dnis: "+ passengersId);
        for (Long dni : passengersId) {
            boolean found = false;
            for (Passenger passenger : passengerList) {
                if (passenger.getDni().equals(dni)) {
                    found = true;
                    //asignar el vuelo a los tripulantes
                    List<Long> assignedFlights = passenger.getAssignedFlights();
                    if(assignedFlights == null){
                        List<Long> flights = new ArrayList<>();
                        flights.add(flightId);
                        passenger.setAssignedFlights(flights);
                    }else{
                        assignedFlights.add(flightId);
                        passenger.setAssignedFlights(assignedFlights);
                    }
                    break;
                }
            }
            if (!found) {
                throw new FlightException("No existen Crew members con esos dnis");
            }
        }
        // asigno el vuelo a los tripulantes
        flightClient.updateAllPassengers(passengerList);
        //asigno los tripulantes al vuelo

        Flight existingFlight = flight.get();
        List<Long> assignedPassengers= existingFlight.getAssignedPassengers();
        if(assignedPassengers == null){
            existingFlight.setAssignedCrew(passengersId);
        }else{
            assignedPassengers.addAll(passengersId);
            existingFlight.setAssignedCrew(assignedPassengers);
        }
        flightRepository.save(existingFlight);
        return flightUtils.flightMapper(existingFlight, getDolarTarjeta());
    }
    public Optional<Flight> updateFlight(Long id, Flight updatedFlight){
        Flight existingFlight = flightRepository.findById(id).orElse(null);
        if (existingFlight != null) {
            existingFlight.setOrigin(updatedFlight.getOrigin());
            existingFlight.setDestination(updatedFlight.getDestination());
            existingFlight.setDepartureDate(updatedFlight.getDepartureDate());
            existingFlight.setPrice(updatedFlight.getPrice());
            existingFlight.setFrequency(updatedFlight.getFrequency());
            existingFlight.setCompany(updatedFlight.getCompany());
            existingFlight.setAssignedCrew(updatedFlight.getAssignedCrew());
            existingFlight.setAssignedPassengers(updatedFlight.getAssignedPassengers());
            flightRepository.save(existingFlight);
        } else {
            throw new FlightException("El vuelo con ID " + id + " no existe. No se puede actualizar.");
        }
        return flightRepository.findById(id);
    }

    public void deleteFlight(Long id){
        Flight existingFlight = flightRepository.findById(id).orElse(null);
        if(existingFlight != null)
            flightRepository.deleteById(id);
        else
            throw new FlightException("El vuelo con ID " + id + " no existe, no se puede eliminar");
    }


}

