package by.baranova.javajourney.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import lombok.Data;

import java.util.List;
/**
 * Entity class representing a travel agency.
 */
@Data
@Entity
@Table(name = "travel_agencies")
public class TravelAgency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "travelAgency",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties("travelAgency")
    private List<Journey> journeys;
}
