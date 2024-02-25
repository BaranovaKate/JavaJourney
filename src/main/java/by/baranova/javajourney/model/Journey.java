package by.baranova.javajourney.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "journeys")
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "country", nullable = false, length = 32)
    private String country;

    @Column(name = "town", nullable = false, length = 32)
    private String town;

    @Column(name = "dateToJourney", nullable = false)
    private LocalDate dateToJourney;

    @Column(name = "dateFromJourney", nullable = false)
    private LocalDate dateFromJourney;


    public Journey() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public LocalDate getDateToJourney() {
        return dateToJourney;
    }

    public void setDateToJourney(LocalDate dateToJourney) {
        this.dateToJourney = dateToJourney;
    }

    public LocalDate getDateFromJourney() {
        return dateFromJourney;
    }

    public void setDateFromJourney(LocalDate dateFromJourney) {
        this.dateFromJourney = dateFromJourney;
    }

    public Journey(Long id, String country, String town, LocalDate dateToJourney, LocalDate dateFromJourney) {
        this.id = id;
        this.country = country;
        this.town = town;
        this.dateToJourney = dateToJourney;
        this.dateFromJourney = dateFromJourney;
    }

    @Override
    public String toString() {
        return "Journey{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", town='" + town + '\'' +
                ", dateToJourney=" + dateToJourney +
                ", dateFromJourney=" + dateFromJourney +
                '}';
    }
}
