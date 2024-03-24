package by.baranova.javajourney.repository;

import by.baranova.javajourney.model.Journey;
import by.baranova.javajourney.dto.JourneyDto;
import by.baranova.javajourney.mapper.JourneyMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Repository
public class JourneyRepository {

    /** The Hibernate SessionFactory for database interactions. */
    private final SessionFactory sessionFactory;

    /** The mapper to convert between Journey and JourneyDto. */
    private final JourneyMapper journeyMapper;

    /** Constant for the country field. */
    private static final String CONST_COUNTRY = "country";

    /** Constant for the UPDATE query. */
    private static final String CONST_UPDATE = """
            UPDATE Journey J SET
               J.country = :country,
               J.town = :town,\s
               J.dateToJourney = :dateToJourney,
               J.dateFromJourney = :dateFromJourney,
               J.travelAgency.id = :travel_agency_id
            WHERE J.id = :id""";

    /**
     * Retrieves a list of all journeys along
     * with their associated travel agencies.
     *
     * @return A list of JourneyDto representing all journeys.
     */
    public List<JourneyDto> findAll() {
        final List<Journey> journeys = sessionFactory.fromSession(session -> {
            Query<Journey> query = session.createQuery(
                    "FROM Journey j JOIN FETCH j.travelAgency ", Journey.class);
            return query.list();
        });
        return journeys.stream()
                .map(journeyMapper::toDto)
                .toList();
    }

    public Optional<JourneyDto> findById(final Long id) {
        return Optional.ofNullable(sessionFactory.fromSession(session -> {
            Query<Journey> query = session.createQuery("""
                FROM Journey J JOIN FETCH J.travelAgency\s
                WHERE J.id = :id""", Journey.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        })).map(journeyMapper::toDto);
    }


    public void deleteById(final Long id) {
        Optional<JourneyDto> journeyOptional = findById(id);
        if (journeyOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Journey with id " + id + " not found");
        }
        sessionFactory.inTransaction(session -> {
            final MutationQuery query = session.createMutationQuery("""
                DELETE FROM Journey
                WHERE id = :id
                """);
            query.setParameter("id", id);
            query.executeUpdate();
        });
    }

    public List<JourneyDto> findByCountry(final String country) {
        final List<Journey> journeys = sessionFactory.fromSession(session -> {
            Query<Journey> query = session.createQuery("""
                        FROM Journey J JOIN FETCH J.travelAgency\s
                        WHERE J.country = :country""",
                    Journey.class);
            query.setParameter(CONST_COUNTRY, country);
            return query.list();
        });
        if (journeys == null) {
            return Collections.emptyList();
        } else {
            return journeys.stream()
                    .map(journeyMapper::toDto)
                    .toList();
        }
    }

    public void save(final JourneyDto journeyDto) {
        final Journey journey = journeyMapper.toEntity(journeyDto);
        sessionFactory.inTransaction(session -> session.persist(journey));
    }

    public void update(final Long id, final JourneyDto journey) {
        if (findById(id).isEmpty()) {
            throw new EntityNotFoundException(
                    "Journey with id " + id + " does not exist");
        }
        sessionFactory.inTransaction(session -> {
            final MutationQuery query =
                    session.createMutationQuery(CONST_UPDATE);

            query.setParameter("id", id);
            query.setParameter(CONST_COUNTRY, journey.getCountry());
            query.setParameter("town", journey.getTown());
            query.setParameter("dateToJourney", journey.getDateToJourney());
            query.setParameter("dateFromJourney",
                    journey.getDateFromJourney());
            query.setParameter("travel_agency_id",
                    journey.getTravelAgency().getId());
            query.executeUpdate();
        });
    }

    public List<Journey> findByTravelAgencyId(final Long travelAgencyId) {
        return sessionFactory.fromSession(session -> {
            Query<Journey> query = session.createQuery(
                    "FROM Journey J WHERE J.travelAgency.id = "
                            + ":travelAgencyId", Journey.class);
            query.setParameter("travelAgencyId", travelAgencyId);
            return query.list();
        });
    }
}
