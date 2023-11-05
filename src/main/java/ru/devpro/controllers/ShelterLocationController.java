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

import ru.devpro.dto.ShelterLocationDTO;

import ru.devpro.service.ShelterLocationService;

import java.util.Collection;

@RestController
@RequestMapping("/shelterLocation")
@Tag(name = "Адреса", description = "Методы работы с адресами приюта")
public class ShelterLocationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShelterLocationController.class);
    private final ShelterLocationService shelterLocationService;

    public ShelterLocationController(ShelterLocationService shelterLocationService) {
        this.shelterLocationService = shelterLocationService;
    }
    @PostMapping
    @Operation(
            summary = "Создание местоположения приюта",
            description = "Создает новое местоположение приюта."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Местоположение приюта успешно создано.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ShelterLocationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос. Проверьте входные данные.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<ShelterLocationDTO> createShelterLocation(
            @Parameter(description = "Принимает объект местоположения приюта")
            @RequestBody ShelterLocationDTO shelterLocationDTO
    ) {
        LOGGER.info("Received request to save shelter location: {}", shelterLocationDTO);
        ShelterLocationDTO createdShelterLocation = shelterLocationService.createShelterLocation(shelterLocationDTO);
        return new ResponseEntity<>(createdShelterLocation, HttpStatus.CREATED);
    }
    /*@PostMapping
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Местоположение приюта успешно создано.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ShelterLocationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос. Проверьте входные данные.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })

    public ResponseEntity<ShelterLocationDTO> createShelterLocation(
            @Parameter(description = "Адрес приюта") @RequestParam String address,
            @Parameter(description = "Город") @RequestParam String city,
            @Parameter(description = "Штат") @RequestParam String state,
            @Parameter(description = "Почтовый индекс") @RequestParam String zipcode
    ) {
        LOGGER.info("Received request to save shelter location with address: {}, city: {}, state: {}, zipcode: {}", address, city, state, zipcode);

        ShelterLocationDTO createdShelterLocation = shelterLocationService.createShelterLocation(address, city, state, zipcode);
        return new ResponseEntity<>(createdShelterLocation, HttpStatus.CREATED);
    }*/

    @PutMapping
    @Operation(
            summary = "Изменение информации о местоположении приюта",
            description = "Изменяет информацию о местоположении приюта."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о местоположении приюта успешно изменена.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ShelterLocationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос. Проверьте входные данные.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<ShelterLocationDTO> editShelterLocation(
            @RequestBody ShelterLocationDTO shelterLocationDTO
    ) {
        ShelterLocationDTO updatedShelterLocation = shelterLocationService.editShelterLocation(shelterLocationDTO);
        if (updatedShelterLocation == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedShelterLocation);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление местоположения приюта",
            description = "Удаляет местоположение приюта по его идентификатору."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Местоположение приюта успешно удалено."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Местоположение приюта не найдено.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<Void> deleteShelterLocation(@PathVariable Long id) {
        shelterLocationService.deleteShelterLocation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение информации о местоположении приюта по его идентификатору"
    )
    public ResponseEntity<ShelterLocationDTO> getShelterLocationById(@PathVariable Long id) {
        ShelterLocationDTO shelterLocationDTO = shelterLocationService.findShelterLocationById(id);
        if (shelterLocationDTO != null) {
            return ResponseEntity.ok(shelterLocationDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(
            summary = "Поиск всех местоположений приютов",
            description = "Поиск и возвращает информацию о всех местоположениях приютов."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о местоположениях приютов успешно найдена.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ShelterLocationDTO[].class)
                    )
            )
    })
    public ResponseEntity<Collection<ShelterLocationDTO>> findAllShelterLocations() {
        Collection<ShelterLocationDTO> shelterLocations = shelterLocationService.findAll();
        return ResponseEntity.ok(shelterLocations);
    }



}
