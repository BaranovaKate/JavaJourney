package by.baranova.javajourney.controller;
import by.baranova.javajourney.model.JourneyDto;
import by.baranova.javajourney.service.JourneyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/journeys")
@AllArgsConstructor
public class JourneyController {

    private final JourneyService journeyService;
    static final Logger LOGGER = LogManager.getLogger(JourneyController.class);

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

    @GetMapping("/{id}")
    public ResponseEntity<JourneyDto> findJourney(final @PathVariable("id") Long id) {
        try {
            LOGGER.info("Display Journey by id");
            JourneyDto journey = journeyService.findJourneyById(id);
            return ResponseEntity.ok(journey);
        } catch (EntityNotFoundException e) {
            LOGGER.error("404 Not Found: {} ",  e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete")
    public String handleJourneyDeleteByCountry(final @RequestParam(name =
            "country") String country) {
        journeyService.deleteByCountry(country);
        LOGGER.info("Delete Journey by Country");
        return "Successfully deleted journeys in " + country;
    }

    @PostMapping("/new")
    public String handleJourneyCreation(final @Valid
                                            @RequestBody JourneyDto journey) {
        journeyService.save(journey);
        LOGGER.info("Create Journey");
        return "Successfully created a new journey";
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> handleJourneyUpdate(final @PathVariable Long id,
                                                      final @Valid @RequestBody JourneyDto journey) {
        try {
            journeyService.update(id, journey);
            LOGGER.info("Update Journey");
            return ResponseEntity.ok("Successfully updated journey with id " + id);
        } catch (EntityNotFoundException e) {
            LOGGER.error("404 Not Found: {} ",  e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> handleJourneyDelete(final @PathVariable Long id) {
        try {
            journeyService.deleteById(id);
            LOGGER.info("Delete Journey by id");
            return ResponseEntity.ok("Successfully deleted journey with id " + id);
        } catch (EntityNotFoundException e) {
            LOGGER.error("404 Not Found: {} ",  e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
