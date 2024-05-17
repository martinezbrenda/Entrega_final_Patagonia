package codoacodo.vuelosapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Passenger{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dni;
    private String name;
    private String email;
    private char gender;
    private String seatNumber;
    private List<Long> assignedFlights = new ArrayList<>();
}
