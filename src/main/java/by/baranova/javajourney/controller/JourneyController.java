package by.baranova.javajourney.controller;
import by.baranova.javajourney.model.JourneyDto;
import by.baranova.javajourney.service.JourneyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

/**
 * The JourneyController class handles HTTP requests related to journeys.
 */
@RestController
@RequestMapping("/journeys")
@AllArgsConstructor
public class JourneyController {

    /**
     * The service responsible for managing journeys.
     */
    private final JourneyService journeyService;
    static final Logger LOGGER = LogManager.getLogger(JourneyController.class);

    /**
     * Retrieves a list of journeys based on the provided country parameter.
     *
     * @param country The country parameter to filter journeys (optional).
     * @return A list of journeys based on the provided country
     * or all journeys if country is null.
     */
    @GetMapping
    public List<JourneyDto> findJourneys(final @RequestParam(name =
            "country", required = false) String country) throws InterruptedException {
        if (country != null) {
            LOGGER.info("Display Journeys by country");
            return journeyService.findJourneysByCountry(country);
        } else {
            LOGGER.info("Display all Journeys");
            return journeyService.findJourneys();
        }
    }

    /**
     * Retrieves a specific journey by its ID.
     *
     * @param id The ID of the journey to retrieve.
     * @return The journey with the specified ID.
     * @throws InterruptedException If the thread is interrupted while waiting.
     */
    @GetMapping("/{id}")
    public JourneyDto findJourney(final @PathVariable("id") Long id)
            throws InterruptedException {
        LOGGER.info("Display Journey by id");
        return journeyService.findJourneyById(id);
    }

    /**
     * Deletes journeys based on the provided country.
     *
     * @param country The country parameter to identify journeys for deletion.
     * @return A message indicating the successful
     * deletion of journeys in the specified country.
     */
    @DeleteMapping("/delete")
    public String handleJourneyDeleteByCountry(final @RequestParam(name =
            "country") String country) {
        journeyService.deleteByCountry(country);
        LOGGER.info("Delete Journey by Country");
        return "Successfully deleted journeys in " + country;
    }

    /**
     * Creates a new journey based on the provided journey data.
     *
     * @param journey The journey data for creating a new journey.
     * @return A message indicating the successful creation of a new journey.
     */
    @PostMapping("/new")
    public String handleJourneyCreation(final @Valid
                                            @RequestBody JourneyDto journey) {
        journeyService.save(journey);
        LOGGER.info("Create Journey");
        return "Successfully created a new journey";
    }

    /**
     * Updates an existing journey with the provided journey data.
     *
     * @param id      The ID of the journey to update.
     * @param journey The journey data for updating the existing journey.
     * @return A message indicating the successful update
     * of the journey with the specified ID.
     */
    @PutMapping("/{id}")
    public String handleJourneyUpdate(final @PathVariable Long id,
                                     final @Valid @RequestBody
                                     JourneyDto journey) {
        journeyService.update(id, journey);
        LOGGER.info("Update Journey");
        return "Successfully updated journey with id " + id;
    }

    /**
     * Deletes a specific journey by its ID.
     *
     * @param id The ID of the journey to delete.
     * @return A message indicating the successful deletion
     * of the journey with the specified ID.
     */
    @DeleteMapping("/{id}")
    public String handleJourneyDelete(final @PathVariable Long id) {
        journeyService.deleteById(id);
        LOGGER.info("Delete Journey by id");
        return "Successfully deleted journey with id " + id;
    }
}
