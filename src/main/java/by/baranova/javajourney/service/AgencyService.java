package by.baranova.javajourney.service;
import by.baranova.javajourney.model.Journey;
import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.dto.TravelAgencyDto;
import by.baranova.javajourney.repository.JourneyRepository;
import by.baranova.javajourney.repository.TravelAgencyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
public class AgencyService {

    private final TravelAgencyRepository travelAgencyRepository;

    private final JourneyRepository journeyRepository;

    static final Logger LOGGER = LogManager.getLogger(AgencyService.class);

    public TravelAgency findAgencyById(final Long id) {
        TravelAgency agency = travelAgencyRepository.findById(id);
        if (agency == null) {
            throw new EntityNotFoundException(
                    "Agency with ID " + id + " not found");
        }
        return agency;
    }

    public void save(final TravelAgency travelAgency) {
        travelAgencyRepository.save(travelAgency);
    }

    public void deleteById(final Long id) {
      //  TravelAgency agencyToDelete = findAgencyById(id);
        List<Journey> journeysWithAgency = journeyRepository.findByTravelAgencyId(id);
        journeysWithAgency.forEach(journey -> journeyRepository.deleteById(journey.getId()));
        travelAgencyRepository.deleteById(id);
    }

    public List<TravelAgency> findAgencies() {
        return travelAgencyRepository.findAllWithJourneys();
    }

    public void update(final Long id, final TravelAgencyDto updatedAgency) {
        TravelAgency agency = findAgencyById(id);
        if (agency == null) {
            throw new EntityNotFoundException(
                    "Travel agency with ID " + id + " does not exist");
        }
        travelAgencyRepository.update(id, updatedAgency);
    }
}
