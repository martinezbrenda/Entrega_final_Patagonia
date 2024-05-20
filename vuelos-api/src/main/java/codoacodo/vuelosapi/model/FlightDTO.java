package codoacodo.vuelosapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FlightDTO {

    private Long id;
    private String origin;
    private String destination;
    private String departureDate;
    private String arrivalDate;
    private double convertedPrice;
    private String frequency;
    private Company company;
    private List<String> crew_names;
   private List<String> passenger_names;





}
