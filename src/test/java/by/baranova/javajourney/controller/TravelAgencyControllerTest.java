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
        Long id = 1L;
        TravelAgency travelAgency = new TravelAgency();
        when(agencyService.findAgencyById(id)).thenReturn(travelAgency);

        ResponseEntity<TravelAgency> response = travelAgencyController.getTravelAgencyById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(travelAgency, response.getBody());
    }

    @Test
    void testGetTravelAgencyById_EntityNotFound() {
        Long id = 1L;
        when(agencyService.findAgencyById(id)).thenThrow(EntityNotFoundException.class);

        ResponseEntity<TravelAgency> response = travelAgencyController.getTravelAgencyById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetAllTravelAgenciesWithJourneys() {
        List<TravelAgency> travelAgencies = new ArrayList<>();
        when(agencyService.findAgencies()).thenReturn(travelAgencies);

        List<TravelAgency> result = travelAgencyController.getAllTravelAgenciesWithJourneys();

        assertEquals(travelAgencies, result);
    }

    @Test
    void testCreateTravelAgency() {
        TravelAgency newTravelAgency = new TravelAgency();

        String result = travelAgencyController.createTravelAgency(newTravelAgency);

        assertEquals("Successfully created a new agency", result);
        verify(agencyService, times(1)).save(newTravelAgency);
    }

    @Test
    void testDeleteTravelAgencyById_Success() {
        Long id = 1L;

        ResponseEntity<String> response = travelAgencyController.deleteTravelAgencyById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully deleted the agency with id: " + id, response.getBody());
        verify(agencyService, times(1)).deleteById(id);
    }

    @Test
    void testDeleteTravelAgencyById_EntityNotFound() {
        Long id = 1L;
        doThrow(EntityNotFoundException.class).when(agencyService).deleteById(id);

        ResponseEntity<String> response = travelAgencyController.deleteTravelAgencyById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testHandleAgencyUpdate_Success() {
        Long id = 1L;
        TravelAgencyDto travelAgencyDto = new TravelAgencyDto();

        ResponseEntity<String> response = travelAgencyController.handleAgencyUpdate(id, travelAgencyDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully updated journey with id " + id, response.getBody());
        verify(agencyService, times(1)).update(id, travelAgencyDto);
    }

    @Test
    void testHandleAgencyUpdate_EntityNotFound() {
        Long id = 1L;
        TravelAgencyDto travelAgencyDto = new TravelAgencyDto();
        doThrow(EntityNotFoundException.class).when(agencyService).update(id, travelAgencyDto);

        ResponseEntity<String> response = travelAgencyController.handleAgencyUpdate(id, travelAgencyDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}