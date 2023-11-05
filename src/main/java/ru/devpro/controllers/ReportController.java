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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.devpro.dto.ReportDTO;

import ru.devpro.service.ReportService;
import ru.devpro.service.ReportServiceImpl;

import java.util.Collection;


@RestController
@RequestMapping("/reports")
@Tag(name = "Отчеты", description = "Методы работы с отчетами")
public class ReportController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    @Operation(summary = "Создание отчета", description = "Создает новый отчет.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Отчет успешно создан.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReportDTO.class))
            ),
            @ApiResponse(
                            responseCode = "400",
                            description = "Неверный запрос. Проверьте входные данные.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
    })

    public ResponseEntity<ReportDTO> createReport(
            @Parameter(description = "Принимает объект отчет")
            @RequestBody ReportDTO reportDTO){
            LOGGER.info("Received request to save report: {}", reportDTO);
            ReportDTO createdReport=reportService.createReport(reportDTO);
            return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
            }
    @PutMapping
    @Operation(summary = "Изменение отчета", description = "Изменяет существующий отчет.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Отчет успешно изменен.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReportDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос. Проверьте входные данные.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Отчет не найден.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<ReportDTO> editReport(
            @RequestBody ReportDTO reportDTO) {
        ReportDTO editedReport = reportService.editReport(reportDTO);
        if (editedReport == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editedReport);
    }

    @GetMapping("/{reportId}")
    @Operation(summary = "Получение информации о отчете по ID",
            description = "Возвращает информацию о отчете по его идентификатору.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о отчете успешно найдена.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReportDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Отчет не найден.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Long reportId) {
        ReportDTO reportDTO = reportService.findReportById(reportId);
        if (reportDTO != null) {
            return ResponseEntity.ok(reportDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{reportId}")
    @Operation(summary = "Удаление отчета", description = "Удаляет отчет по его идентификатору.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Отчет успешно удален."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Отчет не найден.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<Void> deleteReport(@PathVariable Long reportId) {
        reportService.deleteReport(reportId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Поиск всех отчетов",
            description = "Поиск и возвращает информацию о всех отчетах.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Информация о отчетах успешно найдена.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReportDTO[].class))
            )
    })
    public ResponseEntity<Collection<ReportDTO>> findAllReports() {
        Collection<ReportDTO> reports = reportService.findAll();
        return ResponseEntity.ok(reports);
    }
}

