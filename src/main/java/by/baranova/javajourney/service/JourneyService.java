package by.baranova.javajourney.service;

import by.baranova.javajourney.model.JourneyDto;
import by.baranova.javajourney.repository.JourneyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JourneyService {

    private final JourneyRepository journeyRepository;

    public JourneyService(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    public List<JourneyDto> findJourneys() {
        return journeyRepository.findAll();
    }

    public Optional<JourneyDto> findJourneyById(Long id) {
        return journeyRepository.findById(id);
    }

    public void deleteById(Long id) {
        journeyRepository.deleteById(id);
    }

    public List<JourneyDto> findJourneysByCountry(String country) {
        return journeyRepository.findByCountry(country);
    }

    public void deleteByCountry(String country) {
        journeyRepository.deleteByCountry(country);
    }

    public void save(JourneyDto journeyDto) {
        journeyRepository.save(journeyDto);
    }

    public void update(Long id, JourneyDto journey) {
        journeyRepository.update(id, journey);
    }
}