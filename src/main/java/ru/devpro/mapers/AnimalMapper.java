package ru.devpro.mapers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.devpro.dto.AnimalDTO;

import ru.devpro.model.Animal;

@Mapper
public interface AnimalMapper {
    AnimalMapper INSTANCE = Mappers.getMapper(AnimalMapper.class);
    Animal toEntity(AnimalDTO animalDTO); // Метод для преобразования DTO в сущность
    AnimalDTO toDTO(Animal animal); // Метод для преобразования сущности в DTO
}
