package by.baranova.javajourney.controller;

import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.service.AgencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travel-agencies")
@AllArgsConstructor
@Tag(name = "Работа с туристическими агентствами",
        description = "Контроллер для управления туристическими агентствами")
public class TravelAgencyController {

    private final AgencyService agencyService;
    private static final Logger LOGGER = LogManager.getLogger(TravelAgencyController.class);

    @GetMapping("/{id}")
    @Operation(
            method = "GET",
            summary = "Получить туристическое агентство по ID",
            description = "Возвращает туристическое агентство по ID"
    )
    public ResponseEntity<TravelAgency> getTravelAgencyById(@PathVariable Long id) {
        try {
            TravelAgency travelAgency = agencyService.findAgencyById(id);
            LOGGER.info("Retrieved Travel Agency by ID: {}", id);
            return ResponseEntity.ok(travelAgency);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    @Operation(
            method = "GET",
            summary = "Получить список всех туристических агентств",
            description = "Возвращает список всех туристических агентств"
    )
    public List<TravelAgency> getAllTravelAgencies() {
        LOGGER.info("Retrieved all Travel Agencies");
        return agencyService.findAgencies();
    }

    @PostMapping("/create")
    @Operation(
            method = "POST",
            summary = "Создать новое туристическое агентство",
            description = "Создает новое туристическое агентство"
    )
    public ResponseEntity<String> createTravelAgency(@RequestBody TravelAgency newTravelAgency) {
        agencyService.save(newTravelAgency);
        LOGGER.info("Created new Travel Agency");
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created a new agency");
    }

    @DeleteMapping("/{id}")
    @Operation(
            method = "DELETE",
            summary = "Удалить туристическое агентство",
            description = "Удаляет туристическое агентство по ID"
    )
    public ResponseEntity<String> deleteTravelAgency(@PathVariable Long id) {
        try {
            agencyService.deleteById(id);
            LOGGER.info("Deleted Travel Agency by ID: {}", id);
            return ResponseEntity.ok("Successfully deleted agency with ID: " + id);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(
            method = "PUT",
            summary = "Обновить туристическое агентство",
            description = "Обновляет существующее туристическое агентство"
    )
    public ResponseEntity<String> updateTravelAgency(@PathVariable Long id, @RequestBody TravelAgency updatedAgency) {
        try {
            agencyService.update(id, updatedAgency);
            LOGGER.info("Updated Travel Agency with ID: {}", id);
            return ResponseEntity.ok("Successfully updated agency with ID: " + id);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}