package by.baranova.javajourney.controller;

import by.baranova.javajourney.model.JourneyDto;
import by.baranova.javajourney.service.JourneyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journeys")
public class JourneyController {

    private final JourneyService journeyService;

    public JourneyController( JourneyService journeyService) {
        this.journeyService = journeyService;
    }

    @GetMapping
    public List<JourneyDto> findJourneys(@RequestParam(name = "country", required = false) String country) {
        if (country != null) {
            return journeyService.findJourneysByCountry(country);
        } else {
            return journeyService.findJourneys();
        }
    }

    @GetMapping("/{id}")
    public JourneyDto findJourney(@PathVariable("id") Long id) throws InterruptedException {
        return journeyService.findJourneyById(id);
    }

    @DeleteMapping("/delete")
    public String handleJourneyDeleteByCountry(@RequestParam(name = "country") String country) {
        journeyService.deleteByCountry(country);
        return "Successfully deleted journeys in " + country;
    }

    @PostMapping("/new")
    public String handleJourneyCreation(@Valid @RequestBody JourneyDto journey) {
        journeyService.save(journey);
        return "Successfully created a new journey";
    }

    @PutMapping("/{id}")
    public String handleJourneyUpdate(@PathVariable Long id, @Valid @RequestBody JourneyDto journey) {
        journeyService.update(id, journey);
        return "Successfully updated journey with id " + id;
    }

    @DeleteMapping("/{id}")
    public String handleJourneyDelete(@PathVariable Long id) {
        journeyService.deleteById(id);
        return "Successfully deleted journey with id " + id;
    }
}