package ru.devpro.service;


import ru.devpro.dto.UserDTO;
import ru.devpro.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserService {

   UserDTO createUser(UserDTO userDTO); // Метод для создания пользователя

    UserDTO editUser(UserDTO userDTO);

    void deleteUserById(Long userId);

    UserDTO findUserById(Long userId);

    Collection<UserDTO> findAll();

}
