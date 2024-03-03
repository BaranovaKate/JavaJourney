package by.baranova.javajourney.mapper;
import by.baranova.javajourney.model.Journey;
import by.baranova.javajourney.model.JourneyDto;
import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.repository.TravelAgencyRepository;
import org.springframework.stereotype.Component;

@Component
public class JourneyMapper {

    public final TravelAgencyRepository travelAgencyRepository;

    public JourneyMapper(TravelAgencyRepository travelAgencyRepository) {
        this.travelAgencyRepository = travelAgencyRepository;
    }

    public JourneyDto toDto(Journey journey) {
        final JourneyDto dto = new JourneyDto();
        dto.setId(journey.getId());
        dto.setCountry(journey.getCountry());
        dto.setTown(journey.getTown());
        dto.setDateToJourney(journey.getDateToJourney());
        dto.setDateFromJourney(journey.getDateFromJourney());
        dto.setTravelAgency(
                TravelAgencyMapper.toDto(journey.getTravelAgency()));
        return dto;
    }

    public Journey toEntity(JourneyDto dto) {
        final Journey entity = new Journey();
        entity.setId(dto.getId());
        entity.setCountry(dto.getCountry());
        entity.setTown(dto.getTown());
        entity.setDateToJourney(dto.getDateToJourney());
        entity.setDateFromJourney(dto.getDateFromJourney());
        TravelAgency travelAgency = travelAgencyRepository.findById(dto.getTravelAgency().getId());
        entity.setTravelAgency(travelAgency);

        return entity;
    }
}
