package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.Cat;
import com.example.animal_shelter.animal_shelter.service.CatService;
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
 * <b>Контроллер CatController предназначен для вывода дынных кошек
 * с использованием методов прописанных в CatService.</b>
 * Реализованы POST, PUT, DELETE запросы.
 * В дальнейшем будут добавлено множество других удобных запросов, для работы
 * с информацией о кошках и их владельцах.
 */
@RestController
@RequestMapping("cats")
public class CatController {
    private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @Operation(
            summary = "Вносим информацию о новой кошке",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Внесенная информация о кошке ",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Cat.class))
                            )
                    )
            },
            tags = "Cats"
    )
    @PostMapping //POST http://localhost:8080/cats
    public Cat createCat(@Parameter(description = "Необходимо корректно" +
            " заполнить информацию о кошке(коте)", example = "Васька"
    ) @RequestBody Cat cat) {
        return catService.createCat(cat);
    }


    @Operation(
            summary = "Изменяем ранее внесенную информацию о кошке",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Изменяемая кошка ",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Cat.class))
                            )
                    )
            },
            tags = "Cats"
    )
    @PutMapping //PUT http://localhost:8080/cats
    public Cat editCat(@RequestBody Cat cat1) {
        return catService.editCat(cat1);
    }


    @Operation(
            summary = "Удаляем ранее внесенную информацию о кошке",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаляем кошку ",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Cat.class))
                            )
                    )
            },
            tags = "Cats"
    )
    @DeleteMapping("{id}") //DELETE http://localhost:8080/cats/3
    public ResponseEntity<Void> deleteCat(@PathVariable Long id) {
        catService.deleteCat(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Поиск кошек по кличке, породе , и вывод списка всех кошек",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденные кошки.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Cat.class))
                            )
                    )
            },
            tags = "Cats"
    )
//    @GetMapping //GET http://localhost:8080/cats
//    public ResponseEntity findCat(
//            @Parameter(description = "Кличка кошки, часть клички, прописными или заглавными буквами", example = "пример заполнение:ВАська")
//            @RequestParam(required = false, name = "nameCat") String nameCat,
//            @RequestParam(required = false, name = "breed") String breed) {
//
//        if (nameCat != null && !nameCat.isBlank()) {
//            return ResponseEntity.ok(catService.findCatByNameCat(nameCat));
//        }
//        if (breed != null && !breed.isBlank()) {
//            return ResponseEntity.ok(catService.findCatByBreed(breed));
//        }
//        return ResponseEntity.ok(catService.getAllCats());
//    }

    @GetMapping //GET http://localhost:8080/cats
    public ResponseEntity<?> findCat(
            @Parameter(description = "Кличка кошки, часть клички, прописными или заглавными буквами", example = "пример заполнение:ВАська")
            @RequestParam(required = false, name = "nameCat") String nameCat,
            @RequestParam(required = false, name = "breed") String breed) {
        if (nameCat != null && !nameCat.isBlank()) {
            return ResponseEntity.ok(catService.findCatByNameCat(nameCat));
        }
        if (breed != null && !breed.isBlank()) {
            return ResponseEntity.ok(catService.findCatByBreed(breed));
        }
        return ResponseEntity.ok(catService.getAllCats());
    }
}