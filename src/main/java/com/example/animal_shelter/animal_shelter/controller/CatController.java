package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.Cat;
import com.example.animal_shelter.animal_shelter.service.CatService;
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
                            "пример вносимой информации:.......... ",
                    content = @Content(
                            schema = @Schema(implementation = Cat[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @PostMapping //POST http://localhost:8080/cats
    public Cat createCat(@RequestBody Cat cat) {
        return catService.createCat(cat);
    }
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Изменяе ранее внесенную информацию о кошке ",
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

}