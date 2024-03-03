package by.baranova.javajourney.controller;

import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.repository.TravelAgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/travel-agencies")
public class TravelAgencyController {

    private final TravelAgencyRepository travelAgencyRepository;

    // @Autowired
    public TravelAgencyController(TravelAgencyRepository travelAgencyRepository) {
        this.travelAgencyRepository = travelAgencyRepository;
    }

    @GetMapping("/{id}")
    public TravelAgency getTravelAgencyById(@PathVariable Long id) {
        return travelAgencyRepository.findById(id);
    }

    @GetMapping("/all")
    public List<TravelAgency> getAllTravelAgenciesWithJourneys() {
        return travelAgencyRepository.findAllWithJourneys();
    }
}