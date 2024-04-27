package by.baranova.javajourney.service;

import by.baranova.javajourney.model.Journey;
import by.baranova.javajourney.repository.JourneyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class JourneyService {

    private final JourneyRepository journeyRepository;

    public Journey findJourneyById(final Long id) {
        return journeyRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Journey with ID " + id + " not found"));
    }

    public void deleteById(final Long id) {
        journeyRepository.deleteById(id);
    }

    public List<Journey> findJourneys() {
        return journeyRepository.findAll();
    }

    public void save(final Journey journey) {
        journeyRepository.save(journey);
    }

    public void update(final Long id, final Journey updatedJourney) {
        Journey journey = journeyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Journey with ID " + id + "not found"));

                        journey.setCountry(updatedJourney.getCountry());
        journey.setTown(updatedJourney.getTown());
        journey.setDateFromJourney(updatedJourney.getDateFromJourney());
        journey.setDateToJourney(updatedJourney.getDateToJourney());
        journey.setTravelAgency(updatedJourney.getTravelAgency());

        journeyRepository.save(journey);
    }
}