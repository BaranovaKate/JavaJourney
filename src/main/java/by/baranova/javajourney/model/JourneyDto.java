package by.baranova.javajourney.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
/**
 * Data Transfer Object (DTO) representing a journey.
 * Used for transferring journey data between layers of the application.
 */
@Data
public class JourneyDto implements Serializable {

    /** Constant. */
    private static final int CONST = 20;

    /** Unique identifier for the journey. */
    private Long id;

    /** The country of the journey. */
    @NotBlank
    @Size(min = 2, max = CONST)
    private String country;

    /** The town of the journey. */
    @NotBlank
    @Size(min = 2, max = CONST)
    private String town;

    /** The end date of the journey. */
    @NotNull
    private LocalDate dateToJourney;

    /** The start date of the journey. */
    @NotNull
    private LocalDate dateFromJourney;

    /** The DTO representing the travel agency associated with the journey. */
    private TravelAgencyDto travelAgency;
}
