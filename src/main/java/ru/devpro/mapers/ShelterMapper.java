package ru.devpro.mapers;

import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;
import ru.devpro.dto.ShelterDTO;

import ru.devpro.model.Shelter;


@Mapper
public interface ShelterMapper {
        ShelterMapper INSTANCE = Mappers.getMapper(ShelterMapper.class);
    Shelter toEntity(ShelterDTO shelterDTO); // Метод для преобразования DTO в сущность
    ShelterDTO toDTO(Shelter shelter); // Метод для преобразования сущности в DTO

    }

