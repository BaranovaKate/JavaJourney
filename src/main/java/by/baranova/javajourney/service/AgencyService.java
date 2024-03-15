package by.baranova.javajourney.service;

import by.baranova.javajourney.exception.EntityNotFoundException;
import by.baranova.javajourney.model.Journey;
import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.model.TravelAgencyDto;
import by.baranova.javajourney.repository.JourneyRepository;
import by.baranova.javajourney.repository.TravelAgencyRepository;
import lombok.AllArgsConstructor;
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

    /**
     * Retrieves a list of all travel agencies
     * along with their associated journeys.
     *
     * @return A list of TravelAgency entities.
     */
    public List<TravelAgency> findAgencies() {
        return travelAgencyRepository.findAllWithJourneys();
    }

    /**
     * Retrieves a travel agency by its ID.
     *
     * @param id The ID of the travel agency to retrieve.
     * @return The TravelAgency entity.
     */
    public TravelAgency findAgencyById(final Long id) {
        return travelAgencyRepository.findById(id);
    }

    /**
     * Saves a new travel agency.
     *
     * @param travelAgency The TravelAgency entity to save.
     */
    public void save(final TravelAgency travelAgency) {
        travelAgencyRepository.save(travelAgency);
    }

    /**
     * Deletes a travel agency by its ID, along with its associated journeys.
     *
     * @param id The ID of the travel agency to delete.
     * @throws EntityNotFoundException If the travel agency
     *                                 with the specified ID is not found.
     */
    public void deleteById(final Long id) {
        TravelAgency agencyToDelete = travelAgencyRepository.findById(id);

        if (agencyToDelete == null) {
            throw new EntityNotFoundException("Агентство с id "
                    + id + " не найдено");
        }
        List<Journey> journeysWithAgency =
                journeyRepository.findByTravelAgencyId(id);
        journeysWithAgency.forEach(journey ->
                journeyRepository.deleteById(journey.getId()));

        travelAgencyRepository.deleteById(id);
    }

    /**
     * Updates an existing travel agency by ID.
     *
     * @param id            The ID of the travel agency to update.
     * @param updatedAgency The updated TravelAgencyDto data.
     */
    public void update(final Long id, final TravelAgencyDto updatedAgency) {
        travelAgencyRepository.update(id, updatedAgency);
    }
}
