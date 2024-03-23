package by.baranova.javajourney.controller;

import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.dto.TravelAgencyDto;
import by.baranova.javajourney.service.AgencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TravelAgencyControllerTest {

    @Mock
    private AgencyService agencyService;

    @InjectMocks
    private TravelAgencyController travelAgencyController;


    @Test
    void testGetTravelAgencyById_Success() {
        // Arrange
        Long id = 1L;
        TravelAgency travelAgency = new TravelAgency();
        when(agencyService.findAgencyById(id)).thenReturn(travelAgency);

        // Act
        ResponseEntity<TravelAgency> response = travelAgencyController.getTravelAgencyById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(travelAgency, response.getBody());
    }

    @Test
    void testGetTravelAgencyById_EntityNotFound() {
        // Arrange
        Long id = 1L;
        when(agencyService.findAgencyById(id)).thenThrow(EntityNotFoundException.class);

        // Act
        ResponseEntity<TravelAgency> response = travelAgencyController.getTravelAgencyById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetAllTravelAgenciesWithJourneys() {
        // Arrange
        List<TravelAgency> travelAgencies = new ArrayList<>();
        when(agencyService.findAgencies()).thenReturn(travelAgencies);

        // Act
        List<TravelAgency> result = travelAgencyController.getAllTravelAgenciesWithJourneys();

        // Assert
        assertEquals(travelAgencies, result);
    }

    @Test
    void testCreateTravelAgency() {
        // Arrange
        TravelAgency newTravelAgency = new TravelAgency();

        // Act
        String result = travelAgencyController.createTravelAgency(newTravelAgency);

        // Assert
        assertEquals("Successfully created a new agency", result);
        verify(agencyService, times(1)).save(newTravelAgency);
    }

    @Test
    void testDeleteTravelAgencyById_Success() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<String> response = travelAgencyController.deleteTravelAgencyById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully deleted the agency with id: " + id, response.getBody());
        verify(agencyService, times(1)).deleteById(id);
    }

    @Test
    void testDeleteTravelAgencyById_EntityNotFound() {
        // Arrange
        Long id = 1L;
        doThrow(EntityNotFoundException.class).when(agencyService).deleteById(id);

        // Act
        ResponseEntity<String> response = travelAgencyController.deleteTravelAgencyById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testHandleAgencyUpdate_Success() {
        // Arrange
        Long id = 1L;
        TravelAgencyDto travelAgencyDto = new TravelAgencyDto();

        // Act
        ResponseEntity<String> response = travelAgencyController.handleAgencyUpdate(id, travelAgencyDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully updated journey with id " + id, response.getBody());
        verify(agencyService, times(1)).update(id, travelAgencyDto);
    }

    @Test
    void testHandleAgencyUpdate_EntityNotFound() {
        // Arrange
        Long id = 1L;
        TravelAgencyDto travelAgencyDto = new TravelAgencyDto();
        doThrow(EntityNotFoundException.class).when(agencyService).update(id, travelAgencyDto);

        // Act
        ResponseEntity<String> response = travelAgencyController.handleAgencyUpdate(id, travelAgencyDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}