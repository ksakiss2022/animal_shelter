package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.Cat;
import com.example.animal_shelter.animal_shelter.service.CatService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Вносим информацию о новой кошке " +
                            "пример вносимой информации: id- не заполняем, оставляем 0, " +
                            "breed- указываем породу кошки, " +
                            "nameCat: - клички кошки," +
                            "yearOfBirth: вносите год рождения в формате YYYY," +
                            "description: указываем необходимые данные и комментарии о питомце ",
                    content = @Content(
                            schema = @Schema(implementation = Cat[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @PostMapping //POST http://localhost:8080/cats
    public Cat createCat(@Parameter(description = "Необходимо корректно" +
            " заполнить информацию о кошке(коте)", example = "Васька"
           ) @RequestBody Cat cat) {
        return catService.createCat(cat);
    }
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Изменяем ранее внесенную информацию о кошке ",
                    content = @Content(
                            schema = @Schema(implementation = Cat[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @PutMapping //PUT http://localhost:8080/cats
    public Cat editCat(@RequestBody Cat cat) {
        return catService.editCat(cat);
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Удаляем ранее внесенную информацию о кошке ",
                    content = @Content(
                            schema = @Schema(implementation = Cat[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @DeleteMapping("{id}") //DELETE http://localhost:8080/cats/3
    public ResponseEntity deleteCat(@PathVariable Long id) {
        catService.deleteCat(id);
        return ResponseEntity.ok().build();
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Поиск кошек по кличке, породе , и вывод списка всех кошек ",
                    content = @Content(
                            schema = @Schema(implementation = Cat[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @GetMapping //GET http://localhost:8080/cats
    public ResponseEntity findCat(@Parameter(description =
            "Кличка кошки, часть клички, прописными или заглавными буквами",
            example = "пример заполнение:ВАська")@RequestParam(required = false, name = "Кличка питомца") String nameCat,
                                      @RequestParam(required = false, name = "Порода кошки, к примеру бРитанеЦ") String breed) {
        if (nameCat != null && !nameCat.isBlank()) {
            return ResponseEntity.ok(catService.findCatByNameCat(nameCat));
        }
        if (breed != null && !breed.isBlank()) {
            return ResponseEntity.ok(catService.findCatByBreed(breed));
        }
        return ResponseEntity.ok(catService.getAllCats());
    }

}