package by.baranova.javajourney.mapper;

import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.model.TravelAgencyDto;
import org.springframework.stereotype.Component;

@Component
public class TravelAgencyMapper {

    public static TravelAgencyDto toDto(TravelAgency travelAgency){
        final TravelAgencyDto dto = new TravelAgencyDto();
        dto.setId(travelAgency.getId());
        dto.setName(travelAgency.getName());
        return dto;
    }
}
