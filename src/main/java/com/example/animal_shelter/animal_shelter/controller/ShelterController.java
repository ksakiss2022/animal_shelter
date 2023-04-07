package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.Shelter;
import com.example.animal_shelter.animal_shelter.service.ShelterService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Вносим информацию о новом приюте " +
                            "пример вносимой информации: id- не заполняем, оставляем 0, " +
                            "information: - указываем информацию о приюте, " +
                            "schedule: - расписание работы приюта," +
                            "address: - адрес приюта," +
                            "locationMap: - схема проезда в виде картинки," +
                            "safetyRecommendations: -  общие рекомендации о технике безопасности на территории приюта",
                    content = @Content(
                            schema = @Schema(implementation = Shelter[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @PostMapping //POST http://localhost:8080/shelters
    public Shelter createShelter(@Parameter(description = "Необходимо корректно" +
            " заполнить информацию о приюте", example = "Приют для собак"
    ) @RequestBody Shelter shelter) {
        return shelterService.createShelter(shelter);
    }
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Изменяем ранее внесенную информацию о приюте ",
                    content = @Content(
                            schema = @Schema(implementation = Shelter[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @PutMapping //PUT http://localhost:8080/shelters
    public Shelter editShelter(@RequestBody Shelter shelter) {
        return shelterService.editShelter(shelter);
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Удаляем ранее внесенную информацию о приюте ",
                    content = @Content(
                            schema = @Schema(implementation = Shelter[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @DeleteMapping("{id}") //DELETE http://localhost:8080/shelter/3
    public ResponseEntity deleteShelter(@PathVariable Long id) {
        shelterService.deleteShelter(id);
        return ResponseEntity.ok().build();
    }


}
