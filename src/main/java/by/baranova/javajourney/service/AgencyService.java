package by.baranova.javajourney.service;

import by.baranova.javajourney.cache.Cache;
import by.baranova.javajourney.model.Journey;
import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.model.TravelAgencyDto;
import by.baranova.javajourney.repository.JourneyRepository;
import by.baranova.javajourney.repository.TravelAgencyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing TravelAgency entities.
 */


@AllArgsConstructor
@Service
public class AgencyService {
    /**
     * The repository for TravelAgency entities.
     */
    private final TravelAgencyRepository travelAgencyRepository;
    /**
     * The repository for Journey entities.
     */
    private final JourneyRepository journeyRepository;

    private final Cache cache;

    static final Logger LOGGER = LogManager.getLogger(AgencyService.class);
    /**
     * Retrieves a travel agency by its ID.
     *
     * @param id The ID of the travel agency to retrieve.
     * @return The TravelAgency entity.
     */

    public TravelAgency findAgencyById(final Long id) {
        TravelAgency agency = (TravelAgency) cache.get("agency_" + id);
        if (agency == null) {
            agency = travelAgencyRepository.findById(id);
            cache.put("agency_" + id, agency);
        }else {
            LOGGER.info("Display agency from cache");
        }
        return agency;
    }

    /**
     * Saves a new travel agency.
     *
     * @param travelAgency The TravelAgency entity to save.
     */
    public void save(final TravelAgency travelAgency) {
        travelAgencyRepository.save(travelAgency);
        cache.clear();
    }

    /**
     * Deletes a travel agency by its ID, along with its associated journeys.
     *
     * @param id The ID of the travel agency to delete.
     * @throws EntityNotFoundException If the travel agency
     *                                 with the specified ID is not found.
     */
    public void deleteById(final Long id) {
        TravelAgency agencyToDelete = findAgencyById(id);
        if (agencyToDelete == null) {
            throw new EntityNotFoundException("Агентство с id "
                    + id + " не найдено");
        }
        List<Journey> journeysWithAgency = journeyRepository
                .findByTravelAgencyId(id);
        journeysWithAgency.forEach(journey -> journeyRepository
                .deleteById(journey.getId()));

        travelAgencyRepository.deleteById(id);
        cache.clear();
    }

    /**
     * Retrieves a list of all travel agencies
     * along with their associated journeys.
     *
     * @return A list of TravelAgency entities.
     */
    public List<TravelAgency> findAgencies() {
        List<TravelAgency> agencies = (List<TravelAgency>) cache.get("agencies");
        if (agencies == null) {
            agencies = travelAgencyRepository.findAllWithJourneys();
            cache.put("agencies", agencies);
        }else {
            LOGGER.info("Display Agencies from cache");
        }
        return agencies;
    }

    /**
     * Updates an existing travel agency by ID.
     *
     * @param id            The ID of the travel agency to update.
     * @param updatedAgency The updated TravelAgencyDto data.
     */
    public void update(final Long id, final TravelAgencyDto updatedAgency) {
        travelAgencyRepository.update(id, updatedAgency);
        cache.clear();
    }
}
