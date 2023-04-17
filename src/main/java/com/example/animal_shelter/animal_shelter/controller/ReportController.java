package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.Report;
import com.example.animal_shelter.animal_shelter.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <b>Контроллер ReportController предназначен для вывода дынных об отчётах
 * с использованием методов прописанных в ReportService.</b>
 * Реализованы POST, PUT, DELETE запросы.
 * В дальнейшем будут добавлено множество других удобных запросов, для работы с информацией об отчётах.
 */
@RestController
@RequestMapping("reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(
            summary = "Вносим информацию о новом отчёте.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новый отчёт.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report.class))
                            )
                    )
            },
            tags = "Report"
    )
    @PostMapping //POST http://localhost:8080/reports
    public Report createReport(@RequestBody Report report) {
        return reportService.createReport(report);
    }

    @Operation(
            summary = "Изменяем ранее внесенную информацию об отчёте.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Изменённая информация об отчёте",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report.class))
                            )
                    )
            },
            tags = "Report"
    )
    @PutMapping //PUT http://localhost:8080/reports
    public Report editReport(@RequestBody Report report) {
        return reportService.editReport(report);
    }

    @Operation(
            summary = "Удаляем ранее внесенную информацию об отчёте.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленная информацию об отчёте.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report.class))
                            )
                    )
            },
            tags = "Report"
    )
    @DeleteMapping("{id}") //DELETE http://localhost:8080/reports/3
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Поиск отчётов по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденные отчёты.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report.class))
                            )
                    )
            },
            tags = "Report"
    )
    @GetMapping //GET http://localhost:8080/reports
    public ResponseEntity<?> findReports(@Parameter(description =
            "Id создателя отчёта",
            example = "пример заполнения: 2") @RequestParam(required = false, name = "Id создателя отчёта") Long id) {
        if (id != null) {
            return ResponseEntity.ok(reportService.findReportById(id));
        }
        return ResponseEntity.ok(reportService.getAll());
    }



}