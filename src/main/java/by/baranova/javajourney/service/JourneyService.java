package by.baranova.javajourney.service;

import by.baranova.javajourney.model.JourneyDto;
import by.baranova.javajourney.repository.JourneyRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JourneyService {

    private final JourneyRepository journeyRepository;
    private final Map<Long, List<JourneyDto>> inMemoryJourneyMap;

    public JourneyService(JourneyRepository journeyRepository, Map<Long, List<JourneyDto>> inMemoryJourneyMap) {
        this.journeyRepository = journeyRepository;
        this.inMemoryJourneyMap = inMemoryJourneyMap;
    }

    public List<JourneyDto> findJourneys() {
        if (inMemoryJourneyMap.containsKey(0L)) {
            return inMemoryJourneyMap.get(0L);
        } else {
            List<JourneyDto> journeys = journeyRepository.findAll();
            inMemoryJourneyMap.put(0L, journeys);
            return journeys;
        }
    }

    public JourneyDto findJourneyById(Long id) throws InterruptedException {
        if (inMemoryJourneyMap.containsKey(id)) {
            return inMemoryJourneyMap.get(id).stream().findFirst().orElse(null);
        } else {
            TimeUnit.SECONDS.sleep(3L);
            JourneyDto journey = journeyRepository.findById(id).orElse(null);
            if (journey != null) {
                inMemoryJourneyMap.put(id, List.of(journey));
            }
            return journey;
        }
    }

    public void deleteById(Long id) {
        journeyRepository.deleteById(id);
        inMemoryJourneyMap.remove(id);
    }

    public List<JourneyDto> findJourneysByCountry(String country) {
        Long key = (long) country.hashCode();
        if (inMemoryJourneyMap.containsKey(key)) {
            return inMemoryJourneyMap.get(key);
        } else {
            List<JourneyDto> journeys = journeyRepository.findByCountry(country);
            inMemoryJourneyMap.put(key, journeys);
            return journeys;
        }
    }
    public void deleteByCountry(String country) {
        journeyRepository.deleteByCountry(country);
        inMemoryJourneyMap.clear();
    }


    public void save(JourneyDto journeyDto) {
        journeyRepository.save(journeyDto);
        Long id = journeyDto.getId();
        inMemoryJourneyMap.remove(id);
    }

    public void update(Long id, JourneyDto journey) {
        journeyRepository.update(id, journey);
        inMemoryJourneyMap.remove(id);
    }
}

















//
//@Service
//public class JourneyService {
//
//    private final JourneyRepository journeyRepository;
//
//
//    public JourneyService(JourneyRepository journeyRepository) {
//        this.journeyRepository = journeyRepository;
//    }
//
//
//    public List<JourneyDto> findJourneys() {
//        return journeyRepository.findAll();
//    }
//
//
//    public JourneyDto findJourneyById(Long id) throws InterruptedException {
//        TimeUnit.SECONDS.sleep(3L);
//        return journeyRepository.findById(id).orElse(null);
//    }
//
//
//    public void deleteById(Long id) {
//        journeyRepository.deleteById(id);
//    }
//
//
//    public List<JourneyDto> findJourneysByCountry(String country) {
//        return journeyRepository.findByCountry(country);
//    }
//
//
//    public void deleteByCountry(String country) {
//        journeyRepository.deleteByCountry(country);
//    }
//
//
//    public void save(JourneyDto journeyDto) {
//        journeyRepository.save(journeyDto);
//    }
//
//
//    public void update(Long id, JourneyDto journey) {
//        journeyRepository.update(id, journey);
//    }
//}