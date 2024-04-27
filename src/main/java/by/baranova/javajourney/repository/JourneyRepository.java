package by.baranova.javajourney.repository;

import by.baranova.javajourney.model.Journey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JourneyRepository extends JpaRepository<Journey, Long>{

    @Query("SELECT j FROM Journey j WHERE j.travelAgency.id = :travelAgencyId")
    List<Journey> findByTravelAgencyId(@Param("travelAgencyId") Long travelAgencyId);


    @Modifying
    @Transactional
    @Query("""
        UPDATE Journey J SET
            J.country = :country,
            J.town = :town,
            J.dateToJourney = :dateToJourney,
            J.dateFromJourney = :dateFromJourney,
            J.travelAgency.id = :travel_agency_id
        WHERE J.id = :id
    """)
    void updateJourney(
            @Param("id") Long id,
            @Param("country") String country,
            @Param("town") String town,
            @Param("dateToJourney") LocalDate dateToJourney,
            @Param("dateFromJourney") LocalDate dateFromJourney,
            @Param("travel_agency_id") Long travelAgencyId
    );

}
