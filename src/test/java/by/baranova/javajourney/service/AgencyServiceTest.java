package by.baranova.javajourney.service;
import by.baranova.javajourney.model.Journey;
import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.dto.TravelAgencyDto;
import by.baranova.javajourney.repository.JourneyRepository;
import by.baranova.javajourney.repository.TravelAgencyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgencyServiceTest {

    @Mock
    private TravelAgencyRepository travelAgencyRepository;

    @Mock
    private JourneyRepository journeyRepository;

    @InjectMocks
    private AgencyService agencyService;

    public AgencyServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAgencyById_ExistingId() {
        Long id = 1L;
        TravelAgency expectedAgency = new TravelAgency();
        expectedAgency.setId(id);
        when(travelAgencyRepository.findById(id)).thenReturn(expectedAgency);

        TravelAgency result = agencyService.findAgencyById(id);

        assert result != null;
        assert result.getId().equals(id);
    }

    @Test
    void testFindAgencyById_NonExistingId() {
        Long id = 1L;
        when(travelAgencyRepository.findById(id)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> agencyService.findAgencyById(id));
    }

    @Test
    void testSave() {
        TravelAgency travelAgency = new TravelAgency();

        agencyService.save(travelAgency);

        verify(travelAgencyRepository, times(1)).save(travelAgency);
    }

    @Test
    void testDeleteById_ExistingId() {
        Long id = 1L;
        TravelAgency agencyToDelete = new TravelAgency();
        agencyToDelete.setId(id);
        List<Journey> journeysWithAgency = new ArrayList<>();
        when(travelAgencyRepository.findById(id)).thenReturn(agencyToDelete);
        when(journeyRepository.findByTravelAgencyId(id)).thenReturn(journeysWithAgency);

        agencyService.deleteById(id);

        verify(travelAgencyRepository, times(1)).deleteById(id);
        verify(journeyRepository, times(1)).findByTravelAgencyId(id);
        journeysWithAgency.forEach(journey -> verify(journeyRepository, times(1)).deleteById(journey.getId()));
    }

    @Test
    void testDeleteById_NonExistingId() {
        Long id = 1L;
        when(travelAgencyRepository.findById(id)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> agencyService.deleteById(id));
    }

    @Test
    void testFindAgencies() {
        List<TravelAgency> expectedAgencies = new ArrayList<>();
        when(travelAgencyRepository.findAllWithJourneys()).thenReturn(expectedAgencies);

        List<TravelAgency> result = agencyService.findAgencies();

        assertEquals(expectedAgencies, result);
    }

    @Test
    void testUpdate_ExistingId() {
        // Arrange
        Long id = 1L;
        TravelAgencyDto updatedAgencyDto = new TravelAgencyDto();
        TravelAgency agency = new TravelAgency();
        agency.setId(id);
        when(travelAgencyRepository.findById(id)).thenReturn(agency);

        // Act
        agencyService.update(id, updatedAgencyDto);

        // Assert
        verify(travelAgencyRepository, times(1)).update(id, updatedAgencyDto);
    }

    @Test
    void testUpdate_NonExistingId() {
        // Arrange
        Long id = 1L;
        TravelAgencyDto updatedAgencyDto = new TravelAgencyDto();
        when(travelAgencyRepository.findById(id)).thenReturn(null);

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> agencyService.update(id, updatedAgencyDto));
    }
}
