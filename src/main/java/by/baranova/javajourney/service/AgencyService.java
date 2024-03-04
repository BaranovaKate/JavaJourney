package by.baranova.javajourney.service;

import by.baranova.javajourney.exception.EntityNotFoundException;
import by.baranova.javajourney.model.Journey;
import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.repository.JourneyRepository;
import by.baranova.javajourney.repository.TravelAgencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyService {
    private final TravelAgencyRepository travelAgencyRepository;

    private final JourneyRepository journeyRepository;


    public AgencyService(TravelAgencyRepository travelAgencyRepository, JourneyRepository journeyRepository) {
        this.travelAgencyRepository = travelAgencyRepository;
        this.journeyRepository = journeyRepository;
    }

    public List<TravelAgency> findAgencies() {
        return travelAgencyRepository.findAllWithJourneys();
    }

    public TravelAgency findAgencyById(Long id) {
        return travelAgencyRepository.findById(id);
    }

    public void save(TravelAgency travelAgency) {
        travelAgencyRepository.save(travelAgency);
    }

    public void deleteById(Long id) {
        TravelAgency agencyToDelete = travelAgencyRepository.findById(id);

        if (agencyToDelete == null) {
            throw new EntityNotFoundException("Агентство с id " + id + " не найдено");
        }
        List<Journey> journeysWithAgency = journeyRepository.findByTravelAgencyId(id);
        journeysWithAgency.forEach(journey -> journeyRepository.deleteById(journey.getId()));

        travelAgencyRepository.deleteById(id);
    }
}
