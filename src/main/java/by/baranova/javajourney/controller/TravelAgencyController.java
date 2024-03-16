package by.baranova.javajourney.controller;

import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.model.TravelAgencyDto;
import by.baranova.javajourney.service.AgencyService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    /**
     * The service handling
     * operations related to Travel Agencies.
     */
    private final AgencyService agencyService;

    static final Logger LOGGER = LogManager
            .getLogger(TravelAgencyController.class);

    /**
     * Retrieves a Travel Agency by its ID.
     *
     * @param id The ID of the Travel Agency to retrieve.
     * @return The Travel Agency with the specified ID.
     */
    @GetMapping("/{id}")
    public TravelAgency getTravelAgencyById(final @PathVariable Long id) {
        LOGGER.info("Display Travel Agencies by id");
        return agencyService.findAgencyById(id);
    }

    /**
     * Retrieves all Travel Agencies with their associated Journeys.
     *
     * @return A list of all Travel Agencies with their associated Journeys.
     */
    @GetMapping("/all")
    public List<TravelAgency> getAllTravelAgenciesWithJourneys(){
        LOGGER.info("Display Travel Agencies");
        return agencyService.findAgencies();
    }

    /**
     * Creates a new Travel Agency.
     *
     * @param newTravelAgency The details of the new Travel Agency to create.
     * @return A success message indicating that the Travel Agency was created.
     */
    @PostMapping("/create")
    public String createTravelAgency(
           final @RequestBody TravelAgency newTravelAgency) {
        agencyService.save(newTravelAgency);
        LOGGER.info("Create Travel Agencies");
        return "Successfully created a new agency";
    }

    /**
     * Deletes a Travel Agency by its ID.
     *
     * @param id The ID of the Travel Agency to delete.
     * @return A success message indicating that the Travel Agency was deleted.
     */
    @DeleteMapping("/{id}")
    public String deleteTravelAgencyById(final @PathVariable Long id) {
        agencyService.deleteById(id);
        LOGGER.info("Delete Travel Agencies by id");
        return "Successfully deleted the agency with id: " + id;
    }

    /**
     * Updates a Travel Agency by its ID.
     *
     * @param id           The ID of the Travel Agency to update.
     * @param travelAgency The updated details of the Travel Agency.
     * @return A success message indicating that the Travel Agency was updated.
     */
    @PutMapping("/{id}")
    public String handleAgencyUpdate(final @PathVariable Long id,
                                    final @RequestBody TravelAgencyDto
                                             travelAgency) {
        agencyService.update(id, travelAgency);
        LOGGER.info("Update Travel Agencies by id");
        return "Successfully updated journey with id " + id;
    }
}
