package by.baranova.javajourney.service;

import by.baranova.javajourney.cache.Cache;
import by.baranova.javajourney.model.JourneyDto;
import by.baranova.javajourney.repository.JourneyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;
/**
 * Service class for managing Journey entities.
 */

@AllArgsConstructor
@Service
public class JourneyService {

    private final JourneyRepository journeyRepository;
    private final Cache cache;

    public List<JourneyDto> findJourneys() throws InterruptedException {
        List<JourneyDto> journeys = (List<JourneyDto>) cache.get("journeys");
        if (journeys == null) {
            TimeUnit.SECONDS.sleep(3L);
            journeys = journeyRepository.findAll();
            cache.put("journeys", journeys);
        }
        return journeys;
    }

    public JourneyDto findJourneyById(Long id) throws InterruptedException {
        JourneyDto journey = (JourneyDto) cache.get("journey_" + id);
        if (journey == null) {
            journey = journeyRepository.findById(id).orElse(null);
            cache.put("journey_" + id, journey);
        }
        return journey;
    }

    public void deleteById(Long id) {
        journeyRepository.deleteById(id);
        cache.clear();
    }

    public List<JourneyDto> findJourneysByCountry(String country) {
        List<JourneyDto> journeys = (List<JourneyDto>) cache.get("journeys_" + country);
        if (journeys == null) {
            journeys = journeyRepository.findByCountry(country);
            cache.put("journeys_" + country, journeys);
        }
        return journeys;
    }

    public void deleteByCountry(String country) {
        journeyRepository.deleteByCountry(country);
        cache.clear();
    }

    public void save(JourneyDto journeyDto) {
        journeyRepository.save(journeyDto);
        cache.clear();
    }

    public void update(Long id, JourneyDto journey) {
        journeyRepository.update(id, journey);
        cache.clear();
    }
}
