package ru.devpro.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "shelter")
@Data

public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Приют")
    @NotNull
    @Column(name = "name")
    private String name;

    @Schema(description = "Рекомендации")
    @NotNull
    @Column(name="safety_advise")
    private String safety;
    @Column(name="shelter_time", nullable = false)
    private LocalDateTime dateTime;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="shelter_location_id")
    private ShelterLocation shelterLocation;

    @OneToMany(mappedBy = "shelter",fetch = FetchType.EAGER)
    private Set<Animal> animals;


    public Shelter() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return Objects.equals(id, shelter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


