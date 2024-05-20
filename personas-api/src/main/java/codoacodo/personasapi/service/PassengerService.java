package codoacodo.personasapi.service;

import codoacodo.personasapi.Exception.FlightCrewException;
import codoacodo.personasapi.Exception.PassengerException;
import codoacodo.personasapi.model.FlightCrew;
import codoacodo.personasapi.model.Passenger;
import codoacodo.personasapi.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PassengerService {
    @Autowired
    PassengerRepository passengerRepository;

    public List<Passenger> list() {
        return passengerRepository.findAll();
    }

    public Passenger findByDni(long dni) {
        Optional<Passenger> existingPassenger = passengerRepository.findById(dni);
        if(existingPassenger.isEmpty()){
            throw new PassengerException("No existe pasajero con el dni: " + dni);
        }
        return existingPassenger.get();
    }

    public Passenger add(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    public List<Passenger> addList(List<Passenger> passenger) {
        return passengerRepository.saveAll(passenger);
    }

    public Passenger update(Passenger passenger, long dni) {
        Passenger existingPassenger = passengerRepository.findById(dni).orElse(null);
        if(existingPassenger == null ){
            throw new PassengerException("No existe pasajero con el dni: " + dni);
        }

        existingPassenger.setName(passenger.getName());
        existingPassenger.setGender(passenger.getGender());
        existingPassenger.setSeatNumber(passenger.getSeatNumber());
        existingPassenger.setEmail(passenger.getEmail());
        passengerRepository.save(existingPassenger);
        return passenger;
    }

    public List<Passenger> updateAll(List<Passenger> passengerList) {
        for(Passenger passenger :passengerList){
            Passenger existingPassenger = passengerRepository.findById(passenger.getDni()).orElse(null);
            if(existingPassenger == null ){
                throw new PassengerException("No existe pasajero con el dni: " + passenger.getDni());
            }

            existingPassenger.setName(passenger.getName());
            existingPassenger.setGender(passenger.getGender());
            existingPassenger.setSeatNumber(passenger.getSeatNumber());
            existingPassenger.setEmail(passenger.getEmail());
            passengerRepository.save(existingPassenger);

        }
        return passengerList;
    }
    public String delete(long dni) {
        Optional<Passenger> existingPassenger = passengerRepository.findById(dni);
        if(existingPassenger.isEmpty()){
            throw new PassengerException("No existe pasajero con el dni: " + dni);
        }
        passengerRepository.deleteById(dni);
        return "Eliminado con exito";
    }

    public String deleteList(List<Long> dni) {
        Optional<List<Passenger>> existingFlightCrew = Optional.of(passengerRepository.findAllById(dni));
        passengerRepository.deleteAllById(dni);
        return "Eliminado con exito";
    }


}
