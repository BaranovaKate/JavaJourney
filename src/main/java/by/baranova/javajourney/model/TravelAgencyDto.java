package by.baranova.javajourney.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class TravelAgencyDto {


    @NotNull
    private Long id;

    @NotNull
    private String name;


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


    public TravelAgencyDto() {
    }

    public TravelAgencyDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "TravelAgencyDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
