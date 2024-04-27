package by.baranova.javajourney.repository;


import by.baranova.javajourney.model.TravelAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelAgencyRepository extends  JpaRepository<TravelAgency, Long> {

@Query("SELECT ta FROM TravelAgency ta LEFT JOIN FETCH ta.journeys")
    List<TravelAgency> findAllWithJourneys();


}
