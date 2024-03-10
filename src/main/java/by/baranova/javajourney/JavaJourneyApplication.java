package by.baranova.javajourney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class JavaJourneyApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaJourneyApplication.class, args);
    }

}
