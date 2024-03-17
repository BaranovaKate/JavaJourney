package by.baranova.javajourney.mapper;
import by.baranova.javajourney.model.Journey;
import by.baranova.javajourney.model.JourneyDto;
import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.repository.TravelAgencyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
/**
 * The JourneyMapper class is responsible for converting
 * between Journey entities and JourneyDto
 * data transfer objects.
 */
@AllArgsConstructor
@Component
public class JourneyMapper {
    /** Repository for travel Agencies.
     */
    private final TravelAgencyRepository travelAgencyRepository;

    /**
     * Converts a Journey entity to a JourneyDto data transfer object.
     *
     * @param journey The Journey entity to be converted.
     * @return The corresponding JourneyDto.
     */
    public JourneyDto toDto(final Journey journey) {
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

    /**
     * Converts a JourneyDto data transfer object to a Journey entity.
     *
     * @param dto The JourneyDto to be converted.
     * @return The corresponding Journey entity.
     */
    public Journey toEntity(final JourneyDto dto) {
        final Journey entity = new Journey();
        entity.setId(dto.getId());
        entity.setCountry(dto.getCountry());
        entity.setTown(dto.getTown());
        entity.setDateToJourney(dto.getDateToJourney());
        entity.setDateFromJourney(dto.getDateFromJourney());
        TravelAgency travelAgency = travelAgencyRepository
                .findById(dto.getTravelAgency().getId());
        entity.setTravelAgency(travelAgency);

        return entity;
    }
}
