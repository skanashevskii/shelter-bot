package ru.devpro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ru.devpro.dto.UserDTO;

import ru.devpro.mapers.UserMapper;

import ru.devpro.model.User;
import ru.devpro.repositories.ShelterRepository;
import ru.devpro.repositories.UsersRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UsersRepository usersRepository;

    private final UserMapper userMapper;


    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;

        this.userMapper = UserMapper.INSTANCE;

    }

    @Override
    @Cacheable("user")
    public UserDTO createUser(UserDTO userDTO) {
        LOGGER.info("Received request to save shelter: {}", userDTO);
        User userEntity = userMapper.toEntity(userDTO); // Преобразуйте DTO в сущность
        User savedEntity = usersRepository.save(userEntity);
        return userMapper.toDTO(savedEntity); // Преобразуйте сущность обратно в DTO
    }
    @Override
    @CachePut(value = "user", key="#user.id")
    public UserDTO editUser(UserDTO userDTO) {
        LOGGER.info("Was invoked method for edit user: {}", userDTO);
        // Преобразовать UserDTO в User
        User user = userMapper.toEntity(userDTO);

        // Попытаться найти существующего пользователя
        Optional<User> existingUser = usersRepository.findById(user.getId());

        if (existingUser.isPresent()) {
            // Обновляем информацию о пользователе
            User updatedUser = existingUser.get();
            updatedUser.setName(user.getName());
            updatedUser.setFamily(user.getFamily());
            updatedUser.setRole(user.getRole());
            updatedUser.setTelephone(user.getTelephone());
            updatedUser.setEmail(user.getEmail());
            // Сохранить обновленного пользователя
            usersRepository.save(updatedUser);
            // Преобразовать обновленного пользователя обратно в UserDTO
            return userMapper.toDTO(updatedUser);
        } else {
            // Обработка случая, если пользователь не найден
            return null;
        }
    }

    @Override
    @CacheEvict("user")//удаление кэш
    public void deleteUserById(Long userId) {
        LOGGER.info("Was invoked method for delete user by id: {}", userId);

        // Найти пользователя по ID
        User user = usersRepository.findById(userId).orElse(null);

        // Если пользователь с таким ID найден, удаляем его
        if (user != null) {
            usersRepository.delete(user);
        }
    }
    @Override
    public UserDTO findUserById(Long userId) {
        LOGGER.info("Was invoked method for find user by id: {}", userId);
        Optional<User> userOptional = usersRepository.findById(userId);

       return userOptional.map(userMapper::toDTO).orElse(null);
    }

  @Override
  public Collection<UserDTO> findAll() {
      // Всех пользователей из репозитория
      List<User> users = usersRepository.findAll();

      // Преобразуем список пользователей в список UserDTO с использованием маппера

      return users.stream()
              .map(userMapper::toDTO)
              .collect(Collectors.toList());
  }


}
