package ru.devpro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.devpro.dto.ShelterLocationDTO;
import ru.devpro.model.ShelterLocation;
@Repository
public interface ShelterLocationRepository extends JpaRepository<ShelterLocation,Long> {


}
