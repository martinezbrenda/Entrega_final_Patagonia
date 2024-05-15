package codoacodo.vuelosapi.repository;
import codoacodo.vuelosapi.model.Flight;
import codoacodo.vuelosapi.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FlightRepositoryTest {
    @Autowired
    private  FlightRepository flightRepository;
    private Flight flight;
    @BeforeEach
    void setUp(){
          flight = new Flight("COR","EZE","12-04-2024","12-04-2024",300,"Mensual");
    }
    @Test
    void saveFlightTest(){

        Flight flightBD = flightRepository.save(flight);
        assertThat(flightBD).isNotNull();
        assertThat(flightBD.getId()).isGreaterThan(0);
    }
    @Test
    void flightFindByIdTest(){
        flightRepository.save(flight);
        Flight flightBD = flightRepository.findById(flight.getId()).get();
        assertThat(flightBD).isNotNull();
    }
    @Test
    void flightFindAllTest(){
        flightRepository.save(flight);
        Flight flight1 = new Flight("MEN","EZE","12-04-2024","12-04-2024",300,"Mensual");
        flightRepository.save(flight1);
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).isNotNull();
        assertThat(flightList.size()).isEqualTo(2);
    }
    @Test
    void flightDeleteById(){
        flightRepository.save(flight);
        flightRepository.deleteById(flight.getId());
        Optional<Flight> deletedFlight =flightRepository.findById(flight.getId());
        assertThat(deletedFlight).isEmpty();
    }
    @Test
    void flightUpdateTest(){
        flightRepository.save(flight);
        Flight flightDB = flightRepository.findById(flight.getId()).get();
        flightDB.setOrigin("BRA");
        flightDB.setDestination("ARG");
        Flight flightUpdated = flightRepository.save(flightDB);
        assertThat(flightUpdated.getOrigin()).isEqualTo("BRA");
        assertThat(flightUpdated.getDestination()).isEqualTo("ARG");
    }
}
