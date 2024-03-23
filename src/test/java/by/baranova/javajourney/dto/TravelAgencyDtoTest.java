package by.baranova.javajourney.dto;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
class TravelAgencyDtoTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testTravelAgencyDtoValidationSuccess() {
        // Arrange
        TravelAgencyDto travelAgencyDto = new TravelAgencyDto();
        travelAgencyDto.setId(1L);
        travelAgencyDto.setName("Travel World");

        // Act
        Set<ConstraintViolation<TravelAgencyDto>> violations = validator.validate(travelAgencyDto);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testTravelAgencyDtoValidationFailure() {
        // Arrange
        TravelAgencyDto travelAgencyDto = new TravelAgencyDto();
        travelAgencyDto.setId(null); // Null ID
        travelAgencyDto.setName(""); // Empty name

        // Act
        Set<ConstraintViolation<TravelAgencyDto>> violations = validator.validate(travelAgencyDto);

        // Assert
        assertEquals(2, violations.size());

        // Проверяем, что оба ожидаемых нарушения присутствуют
        boolean idViolationFound = false;
        boolean nameViolationFound = false;
        for (ConstraintViolation<TravelAgencyDto> violation : violations) {
            if ("id".equals(violation.getPropertyPath().toString())) {
                idViolationFound = true;
            }
            if ("name".equals(violation.getPropertyPath().toString())) {
                nameViolationFound = true;
            }
        }
        assertTrue(idViolationFound);
        assertTrue(nameViolationFound);
    }
}