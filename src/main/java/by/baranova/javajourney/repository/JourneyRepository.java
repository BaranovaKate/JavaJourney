package by.baranova.javajourney.repository;

import by.baranova.javajourney.model.Journey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface JourneyRepository extends JpaRepository<Journey, Long>{

    @Query("SELECT j FROM Journey j WHERE j.travelAgency.id = :travelAgencyId")
    List<Journey> findByTravelAgencyId(@Param("travelAgencyId") Long travelAgencyId);

}
