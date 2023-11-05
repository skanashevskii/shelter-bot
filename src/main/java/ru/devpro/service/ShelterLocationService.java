package ru.devpro.service;


import ru.devpro.dto.ShelterLocationDTO;

import ru.devpro.model.ShelterLocation;

import java.util.Collection;

public interface ShelterLocationService {
    void deleteShelterLocation(Long id);

    ShelterLocationDTO editShelterLocation(ShelterLocationDTO shelterLocationDTO);

   ShelterLocationDTO findShelterLocationById(Long userId);

    Collection<ShelterLocationDTO> findAll();

   ShelterLocationDTO createShelterLocation(ShelterLocationDTO shelterLocationDTO); // Метод для создания адреса приюта
    //ShelterLocationDTO createShelterLocation(String address, String city, String state, String zipcode); // Метод для создания адреса приюта



    // Другие методы
}
