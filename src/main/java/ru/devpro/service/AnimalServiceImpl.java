package ru.devpro.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.devpro.dto.AnimalDTO;

import ru.devpro.enums.AnimalType;
import ru.devpro.mapers.AnimalMapper;

import ru.devpro.model.Animal;

import ru.devpro.repositories.AnimalsRepository;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AnimalServiceImpl implements AnimalService{
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalServiceImpl.class);

    private final AnimalsRepository animalsRepository;
    //private final AvatarService avatarService;
    private final AnimalMapper animalMapper;

    public AnimalServiceImpl(AnimalsRepository animalsRepository) {
        this.animalsRepository = animalsRepository;
        //this.avatarService = avatarService;
        this.animalMapper = AnimalMapper.INSTANCE;
    }
    @Override
    public AnimalDTO findAnimalById(Long animalId) {
        LOGGER.debug("Was invoked method for search animal by id: {}", animalId);

        Optional<Animal> animalOptional = animalsRepository.findById(animalId);

        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();
            // маппер для преобразования сущности Animal в DTO
            return animalMapper.toDTO(animal);
        } else {
            return null;
        }
    }


    @Override
    @Transactional
    public AnimalDTO createAnimal(AnimalDTO animalDTO) {
        LOGGER.info("Received request to create an animal: {}", animalDTO);

        // Преобразуйте DTO в сущность Animal
        Animal animalEntity = animalMapper.toEntity(animalDTO);

        // Установите дату и время
        LocalDateTime truncatedDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        animalEntity.setDateTime(truncatedDateTime);

        // Сохраните сущность в репозитории
        Animal savedEntity = animalsRepository.save(animalEntity);

        // Преобразуйте сущность Animal обратно в DTO

        return animalMapper.toDTO(savedEntity);
    }

    /*@Override
    @Transactional
    public AnimalDTO createAnimalWithAvatar(AnimalDTO animalDTO, MultipartFile avatarFile) throws IOException {
        LOGGER.info("Received request to save shelter: {}", animalDTO);

        // Преобразуйте DTO в сущность
        Animal animalEntity = animalMapper.toEntity(animalDTO);

        // Установите дату и время
        LocalDateTime truncatedDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        animalEntity.setDateTime(truncatedDateTime);
        // Сохраните сущность в репозитории
        Animal savedEntity = animalsRepository.save(animalEntity);
        // Преобразуйте сущность Animal обратно в DTO
        AnimalDTO savedAnimalDTO = animalMapper.toDTO(savedEntity);
        // Загрузите фотографию и свяжите ее с животным
        avatarService.uploadAvatar(savedAnimalDTO.getId(), avatarFile);

        return savedAnimalDTO;
    }*/



    @Override
    public AnimalDTO editAnimal(Long id, AnimalDTO animalDTO, AnimalType type) {
        // Проверка валидности типа животного
        if (!isValidAnimalType(type)) {
            LOGGER.warn("Invalid animal type: {}", type);
            throw new IllegalArgumentException("Invalid animal type: " + type);
        }

        // Поиск животного в репозитории по ID
        return animalsRepository.findById(id)
                .map(dbEntity -> {
                    // Обновление полей сущности на основе данных из DTO
                    dbEntity.setName(animalDTO.getName());
                    dbEntity.setBreed(animalDTO.getBreed());
                    dbEntity.setType_animal(type); // Обновление типа животного
                    dbEntity.setText(animalDTO.getText());

                    // Обновление времени изменения записи
                    LocalDateTime now = LocalDateTime.now();
                    dbEntity.setDateTime(now.truncatedTo(ChronoUnit.SECONDS));

                    // Сохранение обновленной сущности в репозитории
                    animalsRepository.save(dbEntity);

                    // Преобразование сущности в DTO для возврата
                    return animalMapper.toDTO(dbEntity);
                })
                .orElse(null);
    }



    @Override
    public void deleteAnimal(Long animalId) {
        LOGGER.info("Was invoked method for delete animal by id: {}", animalId);
        animalsRepository.findById(animalId)
                .map(entity -> {
                    animalsRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }
    @Override
    public Collection<AnimalDTO> findAllByBreedIgnoreCase(String breed) {
        if (breed == null || breed.isEmpty()) {
            return Collections.emptyList();
        }

        List<Animal> animals = animalsRepository.findByBreedIgnoreCase(breed);
        return animals.stream()
                .map(animalMapper::toDTO)
                .collect(Collectors.toList());
    }


    //валидность типа животного
    private boolean isValidAnimalType(AnimalType animalType) {
        for (AnimalType type : AnimalType.values()) {
            if (type == animalType) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<AnimalDTO> findAll() {
        List<Animal> animals = animalsRepository.findAll();
        return animals.stream()
                .map(animalMapper::toDTO)
                .collect(Collectors.toList());
    }
}
