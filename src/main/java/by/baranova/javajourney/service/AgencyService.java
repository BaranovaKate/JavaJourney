package by.baranova.javajourney.service;

import by.baranova.javajourney.exception.EntityNotFoundException;
import by.baranova.javajourney.model.Journey;
import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.model.TravelAgencyDto;
import by.baranova.javajourney.repository.JourneyRepository;
import by.baranova.javajourney.repository.TravelAgencyRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AgencyService {
    private final TravelAgencyRepository travelAgencyRepository;

    private final JourneyRepository journeyRepository;


    public AgencyService(TravelAgencyRepository travelAgencyRepository, JourneyRepository journeyRepository) {
        this.travelAgencyRepository = travelAgencyRepository;
        this.journeyRepository = journeyRepository;
    }

    @Cacheable("agencies")
    public List<TravelAgency> findAgencies() {
        return travelAgencyRepository.findAllWithJourneys();
    }

    @Cacheable("agencies")
    public TravelAgency findAgencyById(Long id) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3L);
        return travelAgencyRepository.findById(id);
    }

    @CacheEvict(value = "agencies", allEntries = true)
    public void save(TravelAgency travelAgency) {
        travelAgencyRepository.save(travelAgency);
    }

    @CacheEvict(value = "agencies", allEntries = true)
    public void deleteById(Long id) {
        TravelAgency agencyToDelete = travelAgencyRepository.findById(id);

        if (agencyToDelete == null) {
            throw new EntityNotFoundException("Агентство с id " + id + " не найдено");
        }
        List<Journey> journeysWithAgency = journeyRepository.findByTravelAgencyId(id);
        journeysWithAgency.forEach(journey -> journeyRepository.deleteById(journey.getId()));

        travelAgencyRepository.deleteById(id);
    }

    @CacheEvict(value = "agencies", allEntries = true)
    public void update(Long id, TravelAgencyDto updatedAgency) {
        travelAgencyRepository.update(id, updatedAgency);
    }
}