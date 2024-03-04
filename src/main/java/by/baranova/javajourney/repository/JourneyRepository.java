package by.baranova.javajourney.repository;

import by.baranova.javajourney.exception.EntityNotFoundException;
import by.baranova.javajourney.model.Journey;
import by.baranova.javajourney.model.JourneyDto;
import by.baranova.javajourney.mapper.JourneyMapper;
import org.hibernate.SessionFactory;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JourneyRepository {

    private final SessionFactory sessionFactory;
    private final JourneyMapper journeyMapper;
    private static final String CONST_COUNTRY = "country";
    private static final String CONST_UPDATE = """
            UPDATE Journey J SET
               J.country = :country,
               J.town = :town,\s
               J.dateToJourney = :dateToJourney,
               J.dateFromJourney = :dateFromJourney,
               J.travelAgency.id = :travel_agency_id
            WHERE J.id = :id""";

    public JourneyRepository(SessionFactory sessionFactory, JourneyMapper journeyMapper) {
        this.sessionFactory = sessionFactory;
        this.journeyMapper = journeyMapper;
    }

    public List<JourneyDto> findAll() {
        final List<Journey> journeys = sessionFactory.fromSession(session -> {
            Query<Journey> query = session.createQuery("FROM Journey j JOIN FETCH j.travelAgency ", Journey.class);
            return query.list();
        });
        return journeys.stream()
                .map(journeyMapper::toDto)
                .toList();
    }

    public Optional<JourneyDto> findById(Long id) {
        return sessionFactory.fromSession(session -> {
            Query<Journey> query = session.createQuery("FROM Journey J JOIN FETCH J.travelAgency WHERE J.id = :id", Journey.class);
            query.setParameter("id", id);
            return query.uniqueResultOptional().map(journeyMapper::toDto);
        });
    }

    public void deleteById(Long id) {
        if (findById(id).isEmpty()) throw new EntityNotFoundException("Путешествие с id " + id + " не найдено");
        sessionFactory.inTransaction(session -> {
            final MutationQuery query = session.createMutationQuery("""
                    DELETE FROM Journey
                    WHERE id = :id
                    """);
            query.setParameter("id", id);
            query.executeUpdate();
        });
    }

    public List<JourneyDto> findByCountry(String country) {
        final List<Journey> journeys = sessionFactory.fromSession(session -> {
            Query<Journey> query = session.createQuery("FROM Journey J JOIN FETCH J.travelAgency WHERE J.country = :country", Journey.class);
            query.setParameter(CONST_COUNTRY, country);
            return query.list();
        });
        return journeys.stream()
                .map(journeyMapper::toDto)
                .toList();
    }

    public void deleteByCountry(String country) {
        if (findByCountry(country).isEmpty())
            throw new EntityNotFoundException("Путешествие в " + country + " не существует");
        sessionFactory.inTransaction(session -> {
            final MutationQuery query = session.createMutationQuery("""
                    DELETE FROM Journey
                    WHERE country = :country
                    """);
            query.setParameter(CONST_COUNTRY, country);
            query.executeUpdate();
        });
    }

    public void save(JourneyDto journeyDto) {
        final Journey journey = journeyMapper.toEntity(journeyDto);
        sessionFactory.inTransaction(session -> session.persist(journey));
    }

    public void update(Long id, JourneyDto journey) {
        if (findById(id).isEmpty()) throw new EntityNotFoundException("Путешествие с id " + id + " не существует");
        sessionFactory.inTransaction(session -> {
            final MutationQuery query = session.createMutationQuery(CONST_UPDATE);

            query.setParameter("id", id);
            query.setParameter(CONST_COUNTRY, journey.getCountry());
            query.setParameter("town", journey.getTown());
            query.setParameter("dateToJourney", journey.getDateToJourney());
            query.setParameter("dateFromJourney", journey.getDateFromJourney());
            query.setParameter("travel_agency_id", journey.getTravelAgency().getId());
            query.executeUpdate();
        });
    }

    public List<Journey> findByTravelAgencyId(Long travelAgencyId) {
        return sessionFactory.fromSession(session -> {
            Query<Journey> query = session.createQuery(
                    "FROM Journey J WHERE J.travelAgency.id = :travelAgencyId", Journey.class);
            query.setParameter("travelAgencyId", travelAgencyId);
            return query.list();
        });
    }
}