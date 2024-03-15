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

    private Long id;

    @NotBlank
    @Size(min = 2, max = 20)
    private String country;

    @NotBlank
    @Size(min = 2, max = 20)
    private String town;

    @NotNull
    private LocalDate dateToJourney;

    @NotNull
    private LocalDate dateFromJourney;

    private TravelAgencyDto travelAgency;
}
