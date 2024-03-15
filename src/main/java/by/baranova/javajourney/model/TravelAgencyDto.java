package by.baranova.javajourney.model;

import lombok.Data;

/**
 * Data Transfer Object (DTO) class representing a travel agency.
 * Used for transferring data between layers of the application.
 */
@Data
public class TravelAgencyDto {

    private Long id;

    private String name;

}
