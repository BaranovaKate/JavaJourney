package by.baranova.javajourney.service;

import by.baranova.javajourney.cache.Cache;
import by.baranova.javajourney.dto.JourneyDto;
import by.baranova.javajourney.repository.JourneyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Service class for managing Journey entities.
 */
@AllArgsConstructor
@Service
public class JourneyService {

    /** The repository for Journey entities. */
    private final JourneyRepository journeyRepository;

    /** The cache for storing journey entities. */
    private final Cache cache;

    /** Logger for logging messages. */
    static final Logger LOGGER = LogManager.getLogger(JourneyService.class);

    /** Sleep duration in seconds. */
    private static final int SLEEP_DURATION_SECONDS = 3;
    /**
     * Retrieves a journey by its ID.
     *
     * @param id The ID of the journey to retrieve.
     * @return The JourneyDto entity.
     * @throws EntityNotFoundException If the journey
     * with the specified ID is not found.
     */
    public JourneyDto findJourneyById(final Long id) {
        JourneyDto journey = (JourneyDto) cache.get("journey_" + id);
        if (journey == null) {
            journey = journeyRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Journey with ID " + id + " not found"));
            cache.put("journey_" + id, journey);
        } else {
            LOGGER.info("Display Journey from cache");
        }
        return journey;
    }

    /**
     * Deletes a journey by its ID.
     *
     * @param id The ID of the journey to delete.
     */
    public void deleteById(final Long id) {
        journeyRepository.deleteById(id);
        cache.clear();
    }

    /**
     * Deletes journeys by country.
     *
     * @param country The country to delete journeys from.
     */
    public void deleteByCountry(final String country) {
        journeyRepository.deleteByCountry(country);
        cache.clear();
    }

    /**
     * Retrieves a list of all journeys.
     *
     * @return A list of JourneyDto entities.
     * @throws InterruptedException If interrupted while sleeping.
     */
    public List<JourneyDto> findJourneys() throws InterruptedException {
        List<JourneyDto> journeys = (List<JourneyDto>) cache.get("journeys");
        if (journeys == null) {
            TimeUnit.SECONDS.sleep(SLEEP_DURATION_SECONDS);
            journeys = journeyRepository.findAll();
            cache.put("journeys", journeys);
        } else {
            LOGGER.info("Display Journeys from cache");
        }
        return journeys;
    }

    /**
     * Retrieves a list of journeys by country.
     *
     * @param country The country to filter the journeys by.
     * @return A list of JourneyDto entities filtered by country.
     */
    public List<JourneyDto> findJourneysByCountry(final String country) {
        List<JourneyDto> journeys = (List<JourneyDto>) cache
                .get("journeys_" + country);
        if (journeys == null) {
            journeys = journeyRepository.findByCountry(country);
            cache.put("journeys_" + country, journeys);
        } else {
            LOGGER.info("Display Journeys from cache");
        }
        return journeys;
    }

    /**
     * Saves a new journey.
     *
     * @param journeyDto The JourneyDto representing the new journey.
     */
    public void save(final JourneyDto journeyDto) {
        journeyRepository.save(journeyDto);
        cache.clear();
    }

    /**
     * Updates an existing journey by ID.
     *
     * @param id      The ID of the journey to update.
     * @param journey The updated JourneyDto data.
     */
    public void update(final Long id, final JourneyDto journey) {
        journeyRepository.update(id, journey);
        cache.clear();
    }
}
