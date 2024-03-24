package by.baranova.javajourney.mapper;

import by.baranova.javajourney.dto.TravelAgencyDto;
import by.baranova.javajourney.model.Journey;
import by.baranova.javajourney.dto.JourneyDto;
import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.repository.TravelAgencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class JourneyMapperTest {

    private JourneyMapper journeyMapper;

    @Mock
    private TravelAgencyRepository travelAgencyRepository;

    @BeforeEach
    void setUp() {
        journeyMapper = new JourneyMapper(travelAgencyRepository);
    }

    @Test
    void testToDto() {
        Journey journey = new Journey();
        journey.setId(1L);
        journey.setCountry("Country");
        journey.setTown("Town");
        journey.setDateToJourney(LocalDate.of(2024, 3, 10));
        journey.setDateFromJourney(LocalDate.of(2024, 3, 5));
        TravelAgency travelAgency = new TravelAgency();
        travelAgency.setId(1L);
        journey.setTravelAgency(travelAgency);

        JourneyDto dto = journeyMapper.toDto(journey);

        assertEquals(journey.getId(), dto.getId());
        assertEquals(journey.getCountry(), dto.getCountry());
        assertEquals(journey.getTown(), dto.getTown());
        assertEquals(journey.getDateToJourney(), dto.getDateToJourney());
        assertEquals(journey.getDateFromJourney(), dto.getDateFromJourney());
        assertEquals(journey.getTravelAgency().getId(), dto.getTravelAgency().getId());
    }

    @Test
    void testToEntity() {
        JourneyDto dto = new JourneyDto();
        dto.setId(1L);
        dto.setCountry("Country");
        dto.setTown("Town");
        dto.setDateToJourney(LocalDate.of(2024, 3, 10));
        dto.setDateFromJourney(LocalDate.of(2024, 3, 5));
        TravelAgencyDto travelAgencyDto = new TravelAgencyDto();
        travelAgencyDto.setId(1L);
        dto.setTravelAgency(travelAgencyDto);

        TravelAgency travelAgency = new TravelAgency();
        travelAgency.setId(1L);
        when(travelAgencyRepository.findById(dto.getTravelAgency().getId())).thenReturn(travelAgency);

        Journey journey = journeyMapper.toEntity(dto);

        assertEquals(dto.getId(), journey.getId());
        assertEquals(dto.getCountry(), journey.getCountry());
        assertEquals(dto.getTown(), journey.getTown());
        assertEquals(dto.getDateToJourney(), journey.getDateToJourney());
        assertEquals(dto.getDateFromJourney(), journey.getDateFromJourney());
        assertEquals(dto.getTravelAgency().getId(), journey.getTravelAgency().getId());
    }
}