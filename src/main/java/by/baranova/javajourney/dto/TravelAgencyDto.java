package by.baranova.javajourney.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.io.Serializable;

/**
 * Data Transfer Object (DTO) class representing a travel agency.
 * Used for transferring data between layers of the application.
 */
@Data
public class TravelAgencyDto implements Serializable {

    /** The unique identifier of the travel agency. */
    @NotNull
    private Long id;

    /** The name of the travel agency. */
    @NotNull
    private String name;

}
