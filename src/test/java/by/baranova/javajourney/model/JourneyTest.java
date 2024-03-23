package by.baranova.javajourney.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JourneyTest {

    @Test
    void testJourneyConstructorAndGetters() {
        // Arrange
        Long id = 1L;
        String country = "Country";
        String town = "Town";
        LocalDate dateToJourney = LocalDate.of(2024, 3, 15);
        LocalDate dateFromJourney = LocalDate.of(2024, 3, 20);
        TravelAgency travelAgency = new TravelAgency();

        // Act
        Journey journey = new Journey();
        journey.setId(id);
        journey.setCountry(country);
        journey.setTown(town);
        journey.setDateToJourney(dateToJourney);
        journey.setDateFromJourney(dateFromJourney);
        journey.setTravelAgency(travelAgency);

        // Assert
        assertEquals(id, journey.getId());
        assertEquals(country, journey.getCountry());
        assertEquals(town, journey.getTown());
        assertEquals(dateToJourney, journey.getDateToJourney());
        assertEquals(dateFromJourney, journey.getDateFromJourney());
        assertEquals(travelAgency, journey.getTravelAgency());
    }
}