package by.baranova.javajourney.repository;

import by.baranova.javajourney.model.TravelAgency;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TravelAgencyRepository {
    private final SessionFactory sessionFactory;

    public TravelAgencyRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

//    public TravelAgency findById(Long id) {
//        return sessionFactory.fromSession(session -> {
//            Query<TravelAgency> query = session.createQuery("FROM TravelAgency J WHERE J.id = :id", TravelAgency.class);
//            query.setParameter("id", id);
//            return query.uniqueResult();
//        });
//    }
//


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
}