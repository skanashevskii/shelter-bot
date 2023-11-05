package ru.devpro.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.devpro.dto.AnimalDTO;

import ru.devpro.enums.AnimalType;
import ru.devpro.model.Animal;


import java.io.IOException;
import java.util.Collection;
@Service

public interface AnimalService {
    AnimalDTO createAnimal(AnimalDTO animalDTO); // Метод для создания животного

   /* AnimalDTO createAnimalWithAvatar(
            AnimalDTO animalDTO,
            MultipartFile avatarFile) throws IOException; // Метод для создания животного с фотографией*/

    AnimalDTO editAnimal(Long id, AnimalDTO animalDTO, AnimalType type);

    void deleteAnimal(Long animalId);

    AnimalDTO findAnimalById(Long animalId);

    Collection<AnimalDTO> findAll();


    Collection<AnimalDTO> findAllByBreedIgnoreCase(String breed);
}
