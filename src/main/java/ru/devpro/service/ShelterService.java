package ru.devpro.service;

import org.springframework.stereotype.Service;
import ru.devpro.dto.ShelterDTO;
import ru.devpro.dto.ShelterLocationDTO;
import ru.devpro.model.Shelter;


import java.util.Collection;

@Service
public interface ShelterService {
    ShelterDTO createShelter(ShelterDTO shelterDTO); // Метод для создания приюта
    void deleteShelter(Long id);

    //ShelterLocationDTO createShelterLocation(ShelterLocationDTO shelterLocationDTO);

    ShelterDTO editShelter(ShelterDTO shelterDTO);

    ShelterDTO findShelterById(Long shelterId);

    Collection<ShelterDTO> findAll();



    // Другие методы
}

