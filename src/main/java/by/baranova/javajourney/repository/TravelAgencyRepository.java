package by.baranova.javajourney.repository;

import by.baranova.javajourney.exception.EntityNotFoundException;
import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.model.TravelAgencyDto;
import org.hibernate.SessionFactory;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TravelAgencyRepository {
    private final SessionFactory sessionFactory;

    public TravelAgencyRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public TravelAgency findById(Long id) {
        return sessionFactory.fromSession(session -> {
            Query<TravelAgency> query = session.createQuery("FROM TravelAgency J LEFT JOIN FETCH J.journeys WHERE J.id = :id", TravelAgency.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        });
    }

    public List<TravelAgency> findAllWithJourneys() {
        return sessionFactory.fromSession(session -> {
            Query<TravelAgency> query = session.createQuery("FROM TravelAgency J LEFT JOIN FETCH J.journeys", TravelAgency.class);
            return query.list();
        });
    }

    public void save(TravelAgency travelAgency) {
        sessionFactory.inTransaction(session -> session.persist(travelAgency));
    }

    public void deleteById(Long id) {
        if (findById(id) == null) {
            throw new EntityNotFoundException("Агентство с id " + id + " не найдено");
        }
        sessionFactory.inTransaction(session -> {
            final MutationQuery query = session.createMutationQuery("DELETE FROM TravelAgency WHERE id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
        });
    }

    public void update(Long id, TravelAgencyDto updatedAgency) {
        if (findById(id) == null) {
            throw new EntityNotFoundException("Агентство с id " + id + " не существует");
        }
        sessionFactory.inTransaction(session -> {
            final MutationQuery query = session.createMutationQuery("""
                    UPDATE TravelAgency T SET
                       T.name = :name
                    WHERE T.id = :id
                    """);

            query.setParameter("id", id);
            query.setParameter("name", updatedAgency.getName());
            query.executeUpdate();
        });
    }

}