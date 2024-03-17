package by.baranova.javajourney.repository;

import by.baranova.javajourney.model.TravelAgency;
import by.baranova.javajourney.dto.TravelAgencyDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The TravelAgencyRepository class is responsible
 * for managing operations related to travel
 * agencies in the database.
 */
@AllArgsConstructor
@Repository
public class TravelAgencyRepository {

    /**
     * The Hibernate SessionFactory for database interactions.
     */
    private final SessionFactory sessionFactory;

    /**
     * Retrieves a travel agency by its ID along
     * with its associated journeys.
     *
     * @param id The ID of the travel agency to retrieve.
     * @return The travel agency with the specified ID,
     * including its associated journeys.
     */
    public TravelAgency findById(final Long id) {
        return sessionFactory.fromSession(session -> {
            Query<TravelAgency> query = session.createQuery("""
                            FROM TravelAgency J LEFT JOIN FETCH J.journeys\s
                            WHERE J.id = :id""",
                    TravelAgency.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        });
    }

    /**
     * Retrieves all travel agencies along with their
     * associated journeys.
     *
     * @return A list of travel agencies, each including
     * its associated journeys.
     */
    public List<TravelAgency> findAllWithJourneys() {
        return sessionFactory.fromSession(session -> {
            Query<TravelAgency> query = session
                    .createQuery(
                            "FROM TravelAgency J LEFT JOIN FETCH J.journeys",
                            TravelAgency.class);
            return query.list();
        });
    }

    /**
     * Saves a new travel agency to the database.
     *
     * @param travelAgency The travel agency to be saved.
     */
    public void save(final TravelAgency travelAgency) {
        sessionFactory.inTransaction(session -> session.persist(travelAgency));
    }

    /**
     * Deletes a travel agency by its ID.
     *
     * @param id The ID of the travel agency to delete.
     * @throws EntityNotFoundException If the travel agency
     *                                 with the specified ID is not found.
     */

    public void deleteById(final Long id) {
        TravelAgency agencyToDelete = findById(id);
        if (agencyToDelete == null) {
            throw new EntityNotFoundException(
                    "Агентство с id " + id + " не найдено");
        }

        sessionFactory.inTransaction(session -> {
            final MutationQuery query = session
                    .createMutationQuery(
                            "DELETE FROM TravelAgency WHERE id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
        });
    }


    /**
     * Updates a travel agency's information based
     * on the provided ID and updated data.
     *
     * @param id            The ID of the travel agency to update.
     * @param updatedAgency The updated data for the travel agency.
     * @throws EntityNotFoundException If the travel
     * agency with the specified ID is not found.
     */
    public void update(final Long id, final TravelAgencyDto updatedAgency) {
        if (findById(id) == null) {
            throw new EntityNotFoundException(
                    "Агентство с id " + id + " не существует");
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
