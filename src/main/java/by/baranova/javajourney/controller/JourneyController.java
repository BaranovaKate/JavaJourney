package by.baranova.javajourney.controller;

import by.baranova.javajourney.model.Journey;
import by.baranova.javajourney.service.JourneyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journeys")
@AllArgsConstructor
@Tag(name = "Работа с путешествиями", description = "Контроллер для управления путешествиями")
public class JourneyController {

    private final JourneyService journeyService;
    private static final Logger LOGGER = LogManager.getLogger(JourneyController.class);

    @GetMapping
    @Operation(summary = "Получить список всех путешествий", description = "Возвращает список всех путешествий")
    public List<Journey> findJourneys() {
            LOGGER.info("Display all Journeys");
            return journeyService.findJourneys();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить путешествие по ID", description = "Возвращает путешествие по заданному ID")
    public ResponseEntity<Journey> findJourney(@PathVariable Long id) {
        try {
            Journey journey = journeyService.findJourneyById(id);
            return ResponseEntity.ok(journey);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Journey not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    @Operation(summary = "Создать новое путешествие", description = "Создает новое путешествие")
    public ResponseEntity<String> createJourney(@Valid @RequestBody Journey newJourney) {
        try {
            journeyService.save(newJourney);
            LOGGER.info("Journey created: {}", newJourney);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created a new journey");
        } catch (Exception e) {
            LOGGER.error("Error creating journey: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating journey");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить путешествие по ID", description = "Обновляет информацию о путешествии")
    public ResponseEntity<String> updateJourney(@PathVariable Long id, @Valid @RequestBody Journey updatedJourney) {
        try {
            journeyService.update(id, updatedJourney);
            LOGGER.info("Journey updated: {}", updatedJourney);
            return ResponseEntity.ok("Successfully updated journey with ID: " + id);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Journey not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey not found");
        } catch (Exception e) {
            LOGGER.error("Error updating journey: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating journey");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить путешествие по ID", description = "Удаляет путешествие по заданному ID")
    public ResponseEntity<String> deleteJourney(@PathVariable Long id) {
        try {
            journeyService.deleteById(id);
            LOGGER.info("Journey deleted");
            return ResponseEntity.ok("Journey deleted");
        } catch (EntityNotFoundException e) {
            LOGGER.error("Journey not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journey not found");
        }
    }
}