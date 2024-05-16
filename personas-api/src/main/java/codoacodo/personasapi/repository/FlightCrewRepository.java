package codoacodo.personasapi.repository;

import codoacodo.personasapi.model.FlightCrew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightCrewRepository extends JpaRepository<FlightCrew, Long> {
}
