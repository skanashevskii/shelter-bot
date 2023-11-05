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
import org.springframework.web.multipart.MultipartFile;
import ru.devpro.dto.AnimalDTO;
import ru.devpro.enums.AnimalType;
import ru.devpro.model.Animal;
import ru.devpro.service.AnimalService;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;


@RestController
@RequestMapping("/animals")
@Tag(name = "Животные", description = "Методы работы с животными")
public class AnimalsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalsController.class);
    private final AnimalService animalService;

    public AnimalsController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping
    @Operation(
            summary = "Создание животного",
            description = "Создание объекта животное"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Животное успешно создано.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AnimalDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос. Проверьте входные данные.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<AnimalDTO> createAnimal(
            @Parameter(description = "Принимает объект животное")
            @RequestBody AnimalDTO animalDTO){
        LOGGER.info("Received request to save animal: {}", animalDTO);
        // Сервис для создания животного
        AnimalDTO createdAnimal = animalService.createAnimal(animalDTO);

        // Проверка, что животное было успешно создано
        if (createdAnimal != null) {
            // Верните успешный результат с созданным животным
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAnimal);
        } else {
            // Если создание не удалось, верните код ошибки
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
   /* @PostMapping("/create")
    @Operation(
            summary = "Создание животного с фото",
            description = "Создание объекта животное с фотографией"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Животное успешно создано.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AnimalDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос. Проверьте входные данные.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<AnimalDTO> createAnimalWithAvatar(
            @Parameter(description = "Принимает объект животное с фотографией")
            @RequestPart("animalDTO") AnimalDTO animalDTO,
            @Parameter(description = "Принимает фотографию животного")
            @RequestPart("avatarFile") MultipartFile avatarFile) {
        LOGGER.info("Received request to save animal with avatar: {}", animalDTO);

        try {
            // Сервис для создания животного с фотографией
            AnimalDTO createdAnimal = animalService.createAnimalWithAvatar(animalDTO, avatarFile);

            // Верните успешный результат с созданным животным
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAnimal);
        } catch (IOException e) {
            // Если возникла ошибка при загрузке фотографии, верните код ошибки
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/
    @PutMapping
    @Operation(
            summary = "Изменение информации о животном",
            description = "Изменение информации о животном."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о животном успешно изменена.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AnimalDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос. Проверьте входные данные.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<AnimalDTO> editAnimal(
            @RequestParam Long id,
            @RequestBody AnimalDTO animalDTO,
            @Parameter(description = "Тип животного (CAT/DOG)") @RequestParam AnimalType type) {
        // Передаем id, animalDTO и type в сервис для обработки
        AnimalDTO foundAnimal = animalService.editAnimal(id, animalDTO,type);
        if (foundAnimal == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(foundAnimal);
    }
    @GetMapping("/{animalId}")
    @Operation(
            summary = "Получение информации о животном по ID",
            description = "Возвращает информацию о животном по его идентификатору."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о животном успешно найдена.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AnimalDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Животное не найдено.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<AnimalDTO> getAnimalById(
            @RequestParam Long animalId) {
        AnimalDTO animalDTO = animalService.findAnimalById(animalId);
        if (animalDTO != null) {
            return ResponseEntity.ok(animalDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("{id}")
    @Operation(
            summary = "Удаление животного",
            description = "Удаляет животное по его идентификатору."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Животное успешно удалено."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Животное не найдено."
            )
    })
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/byBreed")
    @Operation(
            summary = "Сортировка по породе",
            description = "Поиск и возвращает информацию о животных по породе."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о животных успешно найдена.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AnimalDTO[].class, type = "array"))
            )
    })
    public ResponseEntity<Collection<AnimalDTO>> findAnimalsByBreed(
            @RequestParam(required = false) String breed) {
        if (breed != null && !breed.isBlank()) {
            Collection<AnimalDTO> animals = animalService.findAllByBreedIgnoreCase(breed);
            return ResponseEntity.ok(animals);
        }

        return ResponseEntity.ok(Collections.emptyList());
    }
}
