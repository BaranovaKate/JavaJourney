package by.baranova.javajourney.controller;

import by.baranova.javajourney.dto.JourneyDto;
import by.baranova.javajourney.service.JourneyService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JourneyControllerTest {

    @Mock
    private JourneyService journeyService;

    @InjectMocks
    private JourneyController journeyController;

    @Test
    void testFindJourneys() throws InterruptedException {
        List<JourneyDto> journeys = new ArrayList<>();
        when(journeyService.findJourneys()).thenReturn(journeys);

        List<JourneyDto> result = journeyController.findJourneys(null);

        assertEquals(journeys, result);
    }

    @Test
    void testFindJourney() {
        Long id = 1L;
        JourneyDto journeyDto = new JourneyDto();
        when(journeyService.findJourneyById(id)).thenReturn(journeyDto);

        ResponseEntity<JourneyDto> result = journeyController.findJourney(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(journeyDto, result.getBody());
    }

    @Test
    void testHandleJourneyCreation() {
        JourneyDto journeyDto = new JourneyDto();

        String result = journeyController.handleJourneyCreation(journeyDto);

        assertEquals("Successfully created a new journey", result);
        verify(journeyService, times(1)).save(journeyDto);
    }

    @Test
    void testCreateJourneysBulk_Success() {
        List<JourneyDto> journeyDtos = new ArrayList<>();
        String agency = "Agency";
        doNothing().when(journeyService).createJourneysBulk(journeyDtos, agency);

        ResponseEntity<String> result = journeyController.createJourneysBulk(journeyDtos, agency);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Successfully created journeys in bulk for agency: Agency", result.getBody());
        verify(journeyService, times(1)).createJourneysBulk(journeyDtos, agency);
    }

    @Test
    void testHandleJourneyUpdate_Success() {
        Long id = 1L;
        JourneyDto journeyDto = new JourneyDto();
        doNothing().when(journeyService).update(id, journeyDto);

        ResponseEntity<String> result = journeyController.handleJourneyUpdate(id, journeyDto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Successfully updated journey with id 1", result.getBody());
        verify(journeyService, times(1)).update(id, journeyDto);
    }

    @Test
    void testHandleJourneyDelete_Success() {
        Long id = 1L;
        doNothing().when(journeyService).deleteById(id);

        ResponseEntity<String> result = journeyController.handleJourneyDelete(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Successfully deleted journey with id 1", result.getBody());
        verify(journeyService, times(1)).deleteById(id);
    }

    @Test
    void testCreateJourneysBulk_Error() {
        List<JourneyDto> journeyDtos = new ArrayList<>();
        String agency = "Agency";
        String errorMessage = "Error occurred";
        doThrow(new RuntimeException(errorMessage)).when(journeyService).createJourneysBulk(journeyDtos, agency);

        ResponseEntity<String> result = journeyController.createJourneysBulk(journeyDtos, agency);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("Error: " + errorMessage, result.getBody());
        verify(journeyService, times(1)).createJourneysBulk(journeyDtos, agency);
    }

    @Test
    void testHandleJourneyUpdate_Error() {
        Long id = 1L;
        JourneyDto journeyDto = new JourneyDto();
        String errorMessage = "Error occurred";
        doThrow(new EntityNotFoundException(errorMessage)).when(journeyService).update(id, journeyDto);

        ResponseEntity<String> result = journeyController.handleJourneyUpdate(id, journeyDto);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(errorMessage, result.getBody());
        verify(journeyService, times(1)).update(id, journeyDto);
    }

    @Test
    void testHandleJourneyDelete_Error() {
        Long id = 1L;
        String errorMessage = "Error occurred";
        doThrow(new EntityNotFoundException(errorMessage)).when(journeyService).deleteById(id);

        ResponseEntity<String> result = journeyController.handleJourneyDelete(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(errorMessage, result.getBody());
        verify(journeyService, times(1)).deleteById(id);
    }
}