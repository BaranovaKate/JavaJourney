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
    /** Constant. */
    private static final int CONST = 64;

    /** The unique identifier of the travel agency. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** The name of the travel agency. */
    @Column(name = "name", nullable = false, length = CONST)
    private String name;

    /**
     * The list of journeys associated with the travel agency.
     * Lazy fetching strategy is used to load the journeys only when needed.
     * Cascading is set to ALL to apply any
     * operation performed on the travel agency
     * to its associated journeys as well.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "travelAgency",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties("travelAgency")
    private List<Journey> journeys;
}
