package by.baranova.javajourney.dto;

import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JourneyDtoTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testJourneyDtoValidationSuccess() {
        JourneyDto journeyDto = new JourneyDto();
        journeyDto.setCountry("France");
        journeyDto.setTown("Paris");
        journeyDto.setDateFromJourney(LocalDate.of(2024, 3, 10));
        journeyDto.setDateToJourney(LocalDate.of(2024, 3, 15));

        Set<ConstraintViolation<JourneyDto>> violations = validator.validate(journeyDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testJourneyDtoValidationFailure() {
        JourneyDto journeyDto = new JourneyDto();
        journeyDto.setCountry("");
        journeyDto.setTown("London");
        journeyDto.setDateFromJourney(LocalDate.of(2024, 3, 10));
        journeyDto.setDateToJourney(LocalDate.of(2024, 3, 5));

        Set<ConstraintViolation<JourneyDto>> violations = validator.validate(journeyDto);

        assertEquals(2, violations.size());
    }
}
