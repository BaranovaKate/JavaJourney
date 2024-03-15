package by.baranova.javajourney.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;


import java.time.LocalDate;

/**
 * Entity class representing a journey.
 */
@Data
@Entity
@Table(name = "journeys")
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "country", nullable = false, length = 32)
    private String country;

    @Column(name = "town", nullable = false, length = 32)
    private String town;

    @Column(name = "dateToJourney", nullable = false)
    private LocalDate dateToJourney;

    @Column(name = "dateFromJourney", nullable = false)
    private LocalDate dateFromJourney;

    @ManyToOne
    @JoinColumn(name = "travel_agency_id", nullable = false)
    @JsonIgnoreProperties("journeys")
    private TravelAgency travelAgency;
}
