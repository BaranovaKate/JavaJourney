package by.baranova.javajourney.config;

import by.baranova.javajourney.model.Journey;
import by.baranova.javajourney.model.TravelAgency;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Configuration class for setting up Hibernate
 * and creating a SessionFactory bean.
 */
@Configuration
public class HibernateConfig {

    /**
     * Creates and returns a Hibernate SessionFactory bean.
     *
     * @return The configured SessionFactory.
     */
    @Bean
    public SessionFactory sessionFactory() {
        // Build the Hibernate StandardServiceRegistry
        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder().build();

        // Build the Hibernate MetadataSources, add annotated
        // classes, and build the SessionFactory
        return new MetadataSources(registry)
                .addAnnotatedClass(Journey.class)
                .addAnnotatedClass(TravelAgency.class)
                .buildMetadata()
                .buildSessionFactory();
    }
}
