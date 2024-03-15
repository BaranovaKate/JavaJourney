package by.baranova.javajourney.service;

import by.baranova.javajourney.cache.Cache;
import by.baranova.javajourney.model.JourneyDto;
import by.baranova.javajourney.repository.JourneyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
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







//
//
//@AllArgsConstructor
//@Service
//public class JourneyService {
//
//    private final JourneyRepository journeyRepository;
//    private final Map<Long, List<JourneyDto>> inMemoryJourneyMap;
//
//
//    /**
//     * Retrieves a list of all journeys.
//     *
//     * @return A list of JourneyDto entities.
//     */
//    public List<JourneyDto> findJourneys() {
//        if (inMemoryJourneyMap.containsKey(0L)) {
//            return inMemoryJourneyMap.get(0L);
//        } else {
//            List<JourneyDto> journeys = journeyRepository.findAll();
//            inMemoryJourneyMap.put(0L, journeys);
//            return journeys;
//        }
//    }
//
//    /**
//     * Retrieves a journey by its ID.
//     *
//     * @param id The ID of the journey to retrieve.
//     * @return The JourneyDto entity.
//     * @throws InterruptedException If the thread is interrupted during sleep.
//     */
//    public JourneyDto findJourneyById(final Long id)
//            throws InterruptedException {
//        if (inMemoryJourneyMap.containsKey(id)) {
//            return inMemoryJourneyMap.get(id).stream().findFirst().orElse(null);
//        } else {
//            TimeUnit.SECONDS.sleep(3L);
//            JourneyDto journey = journeyRepository.findById(id).orElse(null);
//            if (journey != null) {
//                inMemoryJourneyMap.put(id, List.of(journey));
//            }
//            return journey;
//        }
//    }
//
//    /**
//     * Deletes a journey by its ID, and
//     * removes it from the in-memory cache.
//     *
//     * @param id The ID of the journey to delete.
//     */
//    public void deleteById(final Long id) {
//        journeyRepository.deleteById(id);
//        inMemoryJourneyMap.remove(id);
//    }
//
//    /**
//     * Retrieves a list of journeys by country.
//     *
//     * @param country The country to filter journeys.
//     * @return A list of JourneyDto entities.
//     */
//    public List<JourneyDto> findJourneysByCountry(final String country) {
//        Long key = (long) country.hashCode();
//        if (inMemoryJourneyMap.containsKey(key)) {
//            return inMemoryJourneyMap.get(key);
//        } else {
//            List<JourneyDto> journeys =
//                    journeyRepository.findByCountry(country);
//            inMemoryJourneyMap.put(key, journeys);
//            return journeys;
//        }
//    }
//
//    /**
//     * Deletes journeys by country and clears the in-memory cache.
//     *
//     * @param country The country to delete journeys.
//     */
//    public void deleteByCountry(final String country) {
//        journeyRepository.deleteByCountry(country);
//        inMemoryJourneyMap.clear();
//    }
//
//    /**
//     * Saves a new journey and removes it from the in-memory cache.
//     *
//     * @param journeyDto The JourneyDto entity to save.
//     */
//    public void save(final JourneyDto journeyDto) {
//        journeyRepository.save(journeyDto);
//        Long id = journeyDto.getId();
//        inMemoryJourneyMap.remove(id);
//    }
//
//    /**
//     * Updates an existing journey by ID and
//     * removes it from the in-memory cache.
//     *
//     * @param id      The ID of the journey to update.
//     * @param journey The updated JourneyDto data.
//     */
//    public void update(final Long id, final JourneyDto journey) {
//        journeyRepository.update(id, journey);
//        inMemoryJourneyMap.remove(id);
//    }
//}
