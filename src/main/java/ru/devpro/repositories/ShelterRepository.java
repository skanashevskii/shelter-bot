package ru.devpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.devpro.model.Shelter;
@Repository
public interface ShelterRepository extends JpaRepository<Shelter,Long> {
}
