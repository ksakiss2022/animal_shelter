package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.Shelter;
import com.example.animal_shelter.animal_shelter.service.ShelterService;
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
 * <b>Контроллер ShelterController предназначен для вывода данных приютов
 * с использованием методов прописанных в ShelterService.</b>
 * Реализованы POST, PUT, DELETE запросы.
 * В дальнейшем будут добавлено множество других удобных запросов, для работы
 * с информацией о приютах.
 */
@RestController
@RequestMapping("shelters")
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @Operation(
            summary = "Вносим информацию о новом приюте",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Внесенная информацию о приюте ",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Shelter.class))
                            )
                    )
            }, tags = "Shelters")
    @PostMapping //POST http://localhost:8080/shelters
    public Shelter createShelter(@Parameter(description = "Необходимо корректно" +
            " заполнить информацию о приюте", example = "Приют для животного"
    ) @RequestBody Shelter shelter) {
        return shelterService.createShelter(shelter);
    }

    @Operation(
            summary = "Изменяем ранее внесенную информацию о приюте",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Изменяемый приют",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Shelter.class)
                                    )
                            )
                    )
            }, tags = "Shelters")
    @PutMapping //PUT http://localhost:8080/shelters
    public Shelter editShelter(@RequestBody Shelter shelter) {
        return shelterService.editShelter(shelter);
    }

    @Operation(
            summary = "Удаляем ранее внесенную информацию о приюте",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаляем приют",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Shelter.class)
                                    )
                            )
                    )
            }, tags = "Shelters")
    @DeleteMapping("{id}") //DELETE http://localhost:8080/shelter/3
    public ResponseEntity<Void> deleteShelter(@PathVariable Long id) {
        shelterService.deleteShelter(id);
        return ResponseEntity.ok().build();
    }
}
