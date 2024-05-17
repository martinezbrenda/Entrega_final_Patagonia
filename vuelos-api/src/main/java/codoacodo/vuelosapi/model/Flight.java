package codoacodo.vuelosapi.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String origin;
    private String destination;
    private String departureDate;
    private String arrivalDate;
    private double price;
    private String frequency;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private List<Long> assignedCrew = new ArrayList<>();

    public Flight(String origin, String destination, String departureDate, String arrivalDate, double price, String frequency, Company company) {
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.price = price;
        this.frequency = frequency;
        this.company = company;
        this.assignedCrew.add(0L);
    }
    public Company getCompany() {
        return company;
    }


}

