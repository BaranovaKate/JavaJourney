package by.baranova.javajourney.service;

import by.baranova.javajourney.model.Journey;
import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.repository.JourneyRepository;
import by.baranova.javajourney.repository.TravelAgencyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AgencyService {

    private final TravelAgencyRepository travelAgencyRepository;
    private final JourneyRepository journeyRepository;

    public TravelAgency findAgencyById(final Long id) {
        return travelAgencyRepository.findAById(id);
    }

    public void save(final TravelAgency travelAgency) {
        travelAgencyRepository.save(travelAgency);
    }

    public void deleteById(final Long id) {
        TravelAgency agencyToDelete = findAgencyById(id);

        List<Journey> journeysWithAgency = journeyRepository.findByTravelAgencyId(id);
        journeyRepository.deleteAll(journeysWithAgency);

        travelAgencyRepository.delete(agencyToDelete);
    }

    public List<TravelAgency> findAgencies() {
        return travelAgencyRepository.findAllWithJourneys();
    }

    public void update(final Long id, final TravelAgency updatedAgency) {
        TravelAgency agency = findAgencyById(id);
        agency.setName(updatedAgency.getName());
        travelAgencyRepository.save(agency);
    }
}