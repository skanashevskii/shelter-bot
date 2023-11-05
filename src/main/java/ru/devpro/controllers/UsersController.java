package ru.devpro.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import ru.devpro.dto.UserDTO;

import ru.devpro.exceptions.UserNotFoundException;
import ru.devpro.service.UserService;

import java.util.Collection;


@RestController
@RequestMapping("users")
@Tag(name = "Пользователи", description = "Методы работы с пользователями")
public class UsersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    @Operation(
            summary = "Создание пользователя",
            description = "Создание объекта пользователя."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Пользователь успешно создан.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос. Проверьте входные данные.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<UserDTO> createUser(
            @Parameter(description = "Принимает объект пользователь")
            @RequestBody UserDTO userDTO) {
        LOGGER.info("Received request to save animal: {}", userDTO);
        UserDTO createUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createUser,HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(
            summary = "Изменение информации о пользователе",
            description = "Изменение информации о пользователе."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о пользователе успешно изменена.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос. Проверьте входные данные.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })

    public ResponseEntity<UserDTO> editUser(@RequestBody UserDTO userDTO) {
        UserDTO foundUser = userService.editUser(userDTO);
        if (foundUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundUser);
    }
    @GetMapping("/{userId}")
    @Operation(
            summary = "Получение информации о пользователе по его идентификатору",
            description = "Возвращает информацию о пользователе по его идентификатору."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о пользователе успешно найдена.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        ResponseEntity<UserDTO> response;
        UserDTO userDTO = userService.findUserById(userId);

        if (userDTO != null) {
            response = ResponseEntity.ok(userDTO);
        } else {
            response = ResponseEntity.notFound().build();
        }

        return response;
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление пользователя",
            description = "Удаляет пользователя по его идентификатору."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь успешно удален."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден."
            )
    })

    public void deleteUser(@PathVariable long userId) {
        userService.deleteUserById(userId);

    }


    @GetMapping("/all")
    @Operation(
            summary = "Поиск всех пользователей",
            description = "Поиск и возвращает информацию о всех пользователях."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о пользователях успешно найдена.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDTO[].class))
            )
    })

        public ResponseEntity<Collection<UserDTO>> findAllUsers() {

                return ResponseEntity.ok(userService.findAll());

        }

}

