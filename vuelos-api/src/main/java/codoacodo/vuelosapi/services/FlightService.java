package codoacodo.vuelosapi.services;

import codoacodo.vuelosapi.FlightException.FlightException;
import codoacodo.vuelosapi.configuration.FlightConfiguration;
import codoacodo.vuelosapi.model.Company;
import codoacodo.vuelosapi.model.Dolar;
import codoacodo.vuelosapi.model.Flight;
import codoacodo.vuelosapi.model.FlightDTO;
import codoacodo.vuelosapi.repository.CompanyRepository;
import codoacodo.vuelosapi.repository.FlightRepository;
import codoacodo.vuelosapi.utils.FlightUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
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

    public List<FlightDTO> getAllFlights() {
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

    public Optional<Flight> updateFlight(Long id, Flight updatedFlight){
        Flight existingFlight = flightRepository.findById(id).orElse(null);
        if (existingFlight != null) {
            existingFlight.setOrigin(updatedFlight.getOrigin());
            existingFlight.setDestination(updatedFlight.getDestination());
            existingFlight.setDepartureDate(updatedFlight.getDepartureDate());
            existingFlight.setPrice(updatedFlight.getPrice());
            existingFlight.setFrequency(updatedFlight.getFrequency());
            existingFlight.setCompany(updatedFlight.getCompany());
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

