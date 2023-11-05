package ru.devpro.model;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "shelter_location")
@Data

public class ShelterLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Адрес приюта")
    @NotNull
    @Column(name = "address")
    private String address;

    @Schema(description = "Город")
    @NotNull
    @Column(name = "city")
    private String city;

    @Schema(description = "Район")
    @NotNull
    @Column(name = "state")
    private String state;
    @Schema(description = "Почтовый индекс")
    @Column(name = "zipcode")
    private String zipcode;
    @Column(name="shelter_location_time", nullable = false)
    private LocalDateTime dateTime;

    @OneToOne(mappedBy = "shelterLocation",fetch = FetchType.EAGER)
    private Shelter shelter;

    @OneToMany(mappedBy = "shelterLocation",fetch = FetchType.EAGER)
    private List<Animal> animals;


    public ShelterLocation() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShelterLocation that = (ShelterLocation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
