package by.baranova.javajourney.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class JourneyDto {

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

    public JourneyDto() {
    }

    public TravelAgencyDto getTravelAgency() {
        return travelAgency;
    }

    public void setTravelAgency(TravelAgencyDto travelAgency) {
        this.travelAgency = travelAgency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public LocalDate getDateToJourney() {
        return dateToJourney;
    }

    public void setDateToJourney(LocalDate dateToJourney) {
        this.dateToJourney = dateToJourney;
    }

    public LocalDate getDateFromJourney() {
        return dateFromJourney;
    }

    public JourneyDto(Long id, String country, String town, LocalDate dateToJourney, LocalDate dateFromJourney, TravelAgencyDto travelAgency) {
        this.id = id;
        this.country = country;
        this.town = town;
        this.dateToJourney = dateToJourney;
        this.dateFromJourney = dateFromJourney;
        this.travelAgency = travelAgency;
    }

    public void setDateFromJourney(LocalDate dateFromJourney) {
        this.dateFromJourney = dateFromJourney;
    }

}
