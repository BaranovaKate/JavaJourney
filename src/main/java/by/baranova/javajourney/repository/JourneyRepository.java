package by.baranova.javajourney.repository;

import by.baranova.javajourney.model.Journey;
import by.baranova.javajourney.model.JourneyDto;
import by.baranova.javajourney.mapper.JourneyMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * Repository class for handling CRUD operations related to Journey entities.
 */
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

    /**
     * Retrieves a journey by its ID along with its associated travel agency.
     *
     * @param id The ID of the journey to retrieve.
     * @return An Optional containing the JourneyDto if found, otherwise empty.
     */
    public Optional<JourneyDto> findById(final Long id) {
        return sessionFactory.fromSession(session -> {
            Query<Journey> query = session.createQuery("""
                    FROM Journey J JOIN FETCH J.travelAgency\s
                    WHERE J.id = :id""", Journey.class);
            query.setParameter("id", id);
            return query.uniqueResultOptional().map(journeyMapper::toDto);
        });
    }

    /**
     * Deletes a journey by its ID.
     *
     * @param id The ID of the journey to delete.
     * @throws EntityNotFoundException If the journey
     * with the specified ID is not found.
     */
    public void deleteById(final Long id) {
        if (findById(id).isEmpty()) {
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

    /**
     * Retrieves a list of journeys by country
     * along with their associated travel agencies.
     *
     * @param country The country to filter the journeys by.
     * @return A list of JourneyDto representing
     * journeys in the specified country.
     */
    public List<JourneyDto> findByCountry(final String country) {
        final List<Journey> journeys = sessionFactory.fromSession(session -> {
            Query<Journey> query = session.createQuery("""
                            FROM Journey J JOIN FETCH J.travelAgency\s
                            WHERE J.country = :country""",
                    Journey.class);
            query.setParameter(CONST_COUNTRY, country);
            return query.list();
        });
        return journeys.stream()
                .map(journeyMapper::toDto)
                .toList();
    }

    /**
     * Deletes journeys by country.
     *
     * @param country The country to delete journeys from.
     * @throws EntityNotFoundException If there are
     * no journeys in the specified country.
     */
    public void deleteByCountry(final String country) {
        if (findByCountry(country).isEmpty()) {
            throw new EntityNotFoundException(
                    "Journey in " + country + " does not exist");
        }
        sessionFactory.inTransaction(session -> {
            final MutationQuery query = session.createMutationQuery("""
                    DELETE FROM Journey
                    WHERE country = :country
                    """);
            query.setParameter(CONST_COUNTRY, country);
            query.executeUpdate();
        });
    }

    /**
     * Saves a new journey.
     *
     * @param journeyDto The JourneyDto representing the new journey.
     */
    public void save(final JourneyDto journeyDto) {
        final Journey journey = journeyMapper.toEntity(journeyDto);
        sessionFactory.inTransaction(session -> session.persist(journey));
    }

    /**
     * Updates an existing journey by ID.
     *
     * @param id      The ID of the journey to update.
     * @param journey The updated JourneyDto data.
     * @throws EntityNotFoundException If the journey
     * with the specified ID is not found.
     */
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

    /**
     * Retrieves a list of journeys by travel agency ID.
     *
     * @param travelAgencyId The ID of the travel agency
     * to filter journeys by.
     * @return A list of Journey entities
     * associated with the specified travel agency ID.
     */
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
