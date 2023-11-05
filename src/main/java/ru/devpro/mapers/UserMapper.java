package ru.devpro.mapers;

import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;

import ru.devpro.dto.UserDTO;

import ru.devpro.model.User;

@Mapper

public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserDTO userDTO); // Метод для преобразования DTO в сущность
    UserDTO toDTO(User user); // Метод для преобразования сущности в DTO
}
