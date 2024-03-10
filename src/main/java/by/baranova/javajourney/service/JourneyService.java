package by.baranova.javajourney.service;

import by.baranova.javajourney.model.JourneyDto;
import by.baranova.javajourney.repository.JourneyRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class JourneyService {

    private final JourneyRepository journeyRepository;

    public JourneyService(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    @Cacheable("journeys")
    public List<JourneyDto> findJourneys() {
        return journeyRepository.findAll();
    }

    @Cacheable("journeys")
    public JourneyDto findJourneyById(Long id) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3L);
        return journeyRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = "journeys", allEntries = true)
    public void deleteById(Long id) {
        journeyRepository.deleteById(id);
    }

    @Cacheable("journeys")
    public List<JourneyDto> findJourneysByCountry(String country) {
        return journeyRepository.findByCountry(country);
    }

    @CacheEvict(value = "journeys", allEntries = true)
    public void deleteByCountry(String country) {
        journeyRepository.deleteByCountry(country);
    }

    @CacheEvict(value = "journeys", allEntries = true)
    public void save(JourneyDto journeyDto) {
        journeyRepository.save(journeyDto);
    }

    @CacheEvict(value = "journeys", allEntries = true)
    public void update(Long id, JourneyDto journey) {
        journeyRepository.update(id, journey);
    }
}