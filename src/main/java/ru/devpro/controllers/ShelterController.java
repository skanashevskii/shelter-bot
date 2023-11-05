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
import ru.devpro.dto.ShelterDTO;

import ru.devpro.dto.UserDTO;
import ru.devpro.mapers.ShelterMapper;
import ru.devpro.mapers.UserMapper;
import ru.devpro.model.Shelter;

import ru.devpro.model.User;
import ru.devpro.service.ShelterService;

@RestController
@RequestMapping("/shelters")
@Tag(name = "Приюты", description = "Методы работы с приютами")
public class ShelterController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShelterController.class);
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @PostMapping
    @Operation(summary = "Создание приюта", description = "Создает новый приют.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Приют успешно создан.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Shelter.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос. Проверьте входные данные.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })

    public ResponseEntity<ShelterDTO> createShelter(@Parameter(description = "Принимает объект приют")
                                                    @RequestBody ShelterDTO shelterDTO) {
        LOGGER.info("Received request to save animal: {}", shelterDTO);
        ShelterDTO createdShelter = shelterService.createShelter(shelterDTO);
        return new ResponseEntity<>(createdShelter, HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Изменение информации о приюте",
            description = "Изменяет существующий приют.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о приюте успешно изменена.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ShelterDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Приют не найден.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<ShelterDTO> editShelter(@RequestBody ShelterDTO shelterDTO) {
        ShelterDTO foundShelter = shelterService.editShelter(shelterDTO);
        if (foundShelter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundShelter);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации о приюте по его идентификатору",
            description = "Возвращает информацию о приюте по его идентификатору.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о приюте успешно найдена.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ShelterDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Приют не найден.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<ShelterDTO> getShelterById(@PathVariable Long id) {
        ShelterDTO shelter = shelterService.findShelterById(id);
        if (shelter == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shelter, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление приюта", description = "Удаляет приют по его идентификатору.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Приют успешно удален."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Приют не найден.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<Void> deleteShelter(@PathVariable Long id) {
        shelterService.deleteShelter(id);
        return ResponseEntity.noContent().build();
    }
}
