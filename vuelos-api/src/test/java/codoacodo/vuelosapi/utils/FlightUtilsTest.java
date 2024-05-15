package codoacodo.vuelosapi.utils;

import codoacodo.vuelosapi.model.Flight;
import codoacodo.vuelosapi.repository.FlightRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

class FlightUtilsTest {
    private static final List<Flight> flightList= new ArrayList<>();

    private static FlightUtils flightUtils;

    @BeforeAll
    public static void setUp(){
        flightUtils = new FlightUtils();
        System.out.println("Se esta creando la lista de vuelos\n");
        Flight f1 = new Flight(1L, "Nueva York", "Los Ángeles", "2024-04-25", "2024-04-25", 350.00, "Diaria");
        Flight f2 = new Flight(2L, "Los Ángeles", "Chicago", "2024-04-26", "2024-04-26", 280.75, "Semanal");
        Flight f3 = new Flight(3L, "Chicago", "Miami", "2024-04-27", "2024-04-27", 400.00, "Mensual");
        Flight f4 = new Flight(4L, "Miami", "Las Vegas", "2024-04-28", "2024-04-28", 320.80, "Diaria");
        Flight f5 = new Flight(5L, "Las Vegas", "San Francisco", "2024-04-29", "2024-04-29", 420.25, "Semanal");
        Flight f6 = new Flight(6L, "San Francisco", "Seattle", "2024-04-30", "2024-04-30", 370.60, "Mensual");
        Flight f7 = new Flight(7L, "Seattle", "Nueva York", "2024-05-01", "2024-05-01", 390.40, "Diaria");
        flightList.add(f1);
        flightList.add(f2);
        flightList.add(f3);
        flightList.add(f4);
        flightList.add(f5);
        flightList.add(f6);
        flightList.add(f7);
    }

    @Test
    void offerTest(){
        float offerPrice = 350f;
        List<Flight> expectedList = new ArrayList<>();
        expectedList.add(flightList.get(1));
        expectedList.add(flightList.get(3));
        List<Flight> resultedList = flightUtils.detectOffers(flightList, offerPrice);
        Assertions.assertEquals(expectedList,resultedList);
    }


}