package codoacodo.vuelosapi.repository;

import codoacodo.vuelosapi.model.Company;
import codoacodo.vuelosapi.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByOrigin(String origin);

    List<Flight> findByDestination(String destination);

    List<Flight> findByOriginAndDestination(String origin, String destination);



}
