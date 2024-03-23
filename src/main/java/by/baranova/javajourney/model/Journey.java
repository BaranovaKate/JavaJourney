package by.baranova.javajourney.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;

/**
 * Entity class representing a journey.
 */

@Data
@Entity
@Table(name = "journeys")
public class Journey {
    /** Constant. */
    private static final int CONST = 32;

    /** Unique identifier for the journey. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** The country of the journey. */
    @Column(name = "country", nullable = false, length = CONST)
    private String country;

    /** The town of the journey. */
    @Column(name = "town", nullable = false, length = CONST)
    private String town;

    /** The end date of the journey. */
    @Column(name = "dateToJourney", nullable = false)
    private LocalDate dateToJourney;

    /** The start date of the journey. */
    @Column(name = "dateFromJourney", nullable = false)
    private LocalDate dateFromJourney;

    /** The travel agency associated with the journey. */
    @ManyToOne
    @JoinColumn(name = "travel_agency_id", nullable = false)
    @JsonIgnoreProperties("journeys")
    private TravelAgency travelAgency;
}