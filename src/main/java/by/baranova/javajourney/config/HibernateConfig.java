package by.baranova.javajourney.config;

import by.baranova.javajourney.model.Journey;
import by.baranova.javajourney.model.TravelAgency;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching
public class HibernateConfig {
    @Bean
    public SessionFactory sessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().build();

        return new MetadataSources(registry)
                .addAnnotatedClass(Journey.class)
                .addAnnotatedClass(TravelAgency.class)
                .buildMetadata()
                .buildSessionFactory();
    }

}
