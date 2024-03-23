package by.baranova.javajourney.mapper;

import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.dto.TravelAgencyDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TravelAgencyMapperTest {

    @Test
    void testToDto() {
        // Arrange
        TravelAgency travelAgency = new TravelAgency();
        travelAgency.setId(1L);
        travelAgency.setName("Travel World");

        // Act
        TravelAgencyDto dto = TravelAgencyMapper.toDto(travelAgency);

        // Assert
        assertEquals(travelAgency.getId(), dto.getId());
        assertEquals(travelAgency.getName(), dto.getName());
    }
}