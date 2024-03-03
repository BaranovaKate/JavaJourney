package by.baranova.javajourney.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "travel_agencies")
public class TravelAgency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "travelAgency", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("travelAgency")
    private List<Journey> journeys;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Journey> getJourneys() {
        return journeys;
    }

    public void setJourneys(List<Journey> journeys) {
        this.journeys = journeys;
    }

    @Override
    public String toString() {
        return "TravelAgency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", journeys=" + journeys +
                '}';
    }
}
