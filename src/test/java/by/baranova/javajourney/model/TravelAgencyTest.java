package by.baranova.javajourney.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

class TravelAgencyTest {

    @Test
    void testTravelAgencyConstructorAndGetters() {
        Long id = 1L;
        String name = "Travel Agency";
        List<Journey> journeys = new ArrayList<>();

        TravelAgency travelAgency = new TravelAgency();
        travelAgency.setId(id);
        travelAgency.setName(name);
        travelAgency.setJourneys(journeys);

        assertEquals(id, travelAgency.getId());
        assertEquals(name, travelAgency.getName());
        assertEquals(journeys, travelAgency.getJourneys());
    }
}