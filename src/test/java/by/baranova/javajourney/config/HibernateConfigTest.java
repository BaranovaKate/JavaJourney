package by.baranova.javajourney.config;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HibernateConfigTest {

    @InjectMocks
    private HibernateConfig hibernateConfig;


    @Test
    void testSessionFactoryCreation() {
        SessionFactory sessionFactory;

        sessionFactory = hibernateConfig.sessionFactory();

        assertNotNull(sessionFactory);
    }
}