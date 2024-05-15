package codoacodo.vuelosapi.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class FlightTest {
    private static Flight flight;

    @BeforeAll
    public static void setUP(){
        System.out.println("Se esta creando el vuelo\n");
        flight = new Flight();
    }

    @Test
    public void setAndGetOrigin(){
        String testedOrigin = "EZE";
        flight.setOrigin("EZE");
        Assertions.assertEquals(flight.getOrigin(),testedOrigin);
    }
}