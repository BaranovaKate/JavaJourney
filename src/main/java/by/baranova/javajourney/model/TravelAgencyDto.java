package by.baranova.javajourney.model;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.io.Serializable;

/**
 * Data Transfer Object (DTO) class representing a travel agency.
 * Used for transferring data between layers of the application.
 */
@Data
public class TravelAgencyDto implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private String name;

}
