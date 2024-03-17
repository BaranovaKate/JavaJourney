package by.baranova.javajourney.controller;

import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.model.TravelAgencyDto;
import by.baranova.javajourney.service.AgencyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Controller class for handling HTTP requests related to Travel Agencies.
 */
@RestController
@RequestMapping("/travel-agencies")
@AllArgsConstructor
public class TravelAgencyController {

    /** Constant. */
    private static final String ERROR = "404 Not Found: {}";

    /** Service for managing travel agencies. */
    private final AgencyService agencyService;

    /** Logger for logging messages. */
    static final Logger LOGGER = LogManager
            .getLogger(TravelAgencyController.class);

    /**
     * Retrieves a travel agency by its ID.
     *
     * @param id the ID of the travel agency to retrieve
     * @return the travel agency with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TravelAgency> getTravelAgencyById(
            final @PathVariable Long id) {
        try {
            LOGGER.info("Display Travel Agencies by id");
            TravelAgency travelAgency = agencyService.findAgencyById(id)
                    .getBody();
            return ResponseEntity.ok(travelAgency);
        } catch (EntityNotFoundException e) {
            LOGGER.error(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    /**
     * Retrieves all travel agencies along with their associated journeys.
     *
     * @return list of all travel agencies
     */
    @GetMapping("/all")
    public List<TravelAgency> getAllTravelAgenciesWithJourneys() {
        LOGGER.info("Display Travel Agencies");
        return agencyService.findAgencies();
    }

    /**
     * Creates a new travel agency.
     *
     * @param newTravelAgency the new travel agency to create
     * @return success message
     */
    @PostMapping("/create")
    public String createTravelAgency(
            final @RequestBody TravelAgency newTravelAgency) {
        agencyService.save(newTravelAgency);
        LOGGER.info("Create Travel Agencies");
        return "Successfully created a new agency";
    }

    /**
     * Deletes a travel agency by its ID.
     *
     * @param id the ID of the travel agency to delete
     * @return success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTravelAgencyById(
            final @PathVariable Long id) {
        try {
            agencyService.deleteById(id);
            LOGGER.info("Delete Travel Agencies by id");
            return ResponseEntity
                    .ok("Successfully deleted the agency with id: " + id);
        } catch (EntityNotFoundException e) {
            LOGGER.error(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    /**
     * Updates a travel agency by its ID.
     *
     * @param id the ID of the travel agency to update
     * @param travelAgency the updated travel agency data
     * @return success message
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> handleAgencyUpdate(
            final @PathVariable Long id,
            final @RequestBody TravelAgencyDto travelAgency) {
        try {
            agencyService.update(id, travelAgency);
            LOGGER.info("Update Travel Agencies by id");
            return ResponseEntity
                    .ok("Successfully updated journey with id " + id);
        } catch (EntityNotFoundException e) {
            LOGGER.error(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
