package codoacodo.personasapi.service;

import codoacodo.personasapi.Exception.FlightCrewException;
import codoacodo.personasapi.model.FlightCrew;
import codoacodo.personasapi.repository.FlightCrewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightCrewService {
    @Autowired
    FlightCrewRepository flightCrewRepository;

    public List<FlightCrew> list() {
        return flightCrewRepository.findAll();
    }

    public FlightCrew findById(long dni) {
        Optional<FlightCrew> existingFlightCrew = flightCrewRepository.findById(dni);
        if(existingFlightCrew.isEmpty()){
            throw new FlightCrewException("No existe miembro con el dni: " + dni);
        }
        return existingFlightCrew.get();
    }

    public FlightCrew add(FlightCrew flightCrew) {
        return flightCrewRepository.save(flightCrew);
    }

    public List<FlightCrew> addList(List<FlightCrew> flightCrew) {
        return flightCrewRepository.saveAll(flightCrew);
    }

    public FlightCrew update(FlightCrew flightCrew, long dni) {
        FlightCrew existingFlightCrew = flightCrewRepository.findById(dni).orElse(null);
        if(existingFlightCrew == null ){
            throw new FlightCrewException("No existe miembro con el dni: " + dni);
        }
        existingFlightCrew.setName(flightCrew.getName());
        existingFlightCrew.setGender(flightCrew.getGender());
        existingFlightCrew.setRole(flightCrew.getRole());
        existingFlightCrew.setExperienceHours(flightCrew.getExperienceHours());
        existingFlightCrew.setEmail(flightCrew.getEmail());
        existingFlightCrew.setAssignedFlights(flightCrew.getAssignedFlights());
        flightCrewRepository.save(existingFlightCrew);
        return flightCrew;
    }

    public String delete(long dni) {
        Optional<FlightCrew> existingFlightCrew = flightCrewRepository.findById(dni);
        if(existingFlightCrew.isEmpty()){
            throw new FlightCrewException("No existe miembro con el dni: " + dni);
        }
        flightCrewRepository.deleteById(dni);
        return "Eliminado con exito";
    }

    public String deleteList(List<Long> dni) {
        Optional<List<FlightCrew>> existingFlightCrew = Optional.of(flightCrewRepository.findAllById(dni));
        flightCrewRepository.deleteAllById(dni);
        return "Eliminado con exito";
    }
}
