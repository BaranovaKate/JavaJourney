package by.baranova.javajourney.controller;

import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.model.TravelAgencyDto;
import by.baranova.javajourney.service.AgencyService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/travel-agencies")
public class TravelAgencyController {
    private final AgencyService agencyService;

    public TravelAgencyController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    @GetMapping("/{id}")
    public TravelAgency getTravelAgencyById(@PathVariable Long id) {
        return agencyService.findAgencyById(id);
    }

    @GetMapping("/all")
    public List<TravelAgency> getAllTravelAgenciesWithJourneys() {
        return agencyService.findAgencies();
    }

    @PostMapping("/create")
    public String createTravelAgency(@RequestBody TravelAgency newTravelAgency) {
        agencyService.save(newTravelAgency);
        return "Successfully created a new agency";
    }

    @DeleteMapping("/{id}")
    public String deleteTravelAgencyById(@PathVariable Long id) {
        agencyService.deleteById(id);
        return "Successfully deleted the agency with id: " + id;
    }

    @PutMapping("/{id}")
    public String handleAgencyUpdate(@PathVariable Long id, @RequestBody TravelAgencyDto travelAgency) {
        agencyService.update(id, travelAgency);
        return "Successfully updated journey with id " + id;
    }
}