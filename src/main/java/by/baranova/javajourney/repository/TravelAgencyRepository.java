package by.baranova.javajourney.repository;

import by.baranova.javajourney.model.TravelAgency;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class TravelAgencyRepository {
    private final SessionFactory sessionFactory;

    public TravelAgencyRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public TravelAgency findById(Long id) {
        return sessionFactory.fromSession(session -> {
            Query<TravelAgency> query = session.createQuery("FROM TravelAgency J WHERE J.id = :id", TravelAgency.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        });
    }
}
