package by.baranova.javajourney.config;

import by.baranova.javajourney.model.JourneyDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class InMemoryMapConfig {

    @Bean
    public Map<Long, List<JourneyDto>> inMemoryJourneyMap() {
        return new HashMap<>();
    }
}





