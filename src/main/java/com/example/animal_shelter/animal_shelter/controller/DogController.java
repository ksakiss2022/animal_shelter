package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.Dog;
import com.example.animal_shelter.animal_shelter.service.DogService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <b>Контроллер DogController предназначен для вывода дынных собак
 * с использованием методов прописанных в DogService.</b>
 * Реализованы POST, PUT, DELETE запросы.
 * В дальнейшем будут добавлено множество других удобных запросов, для работы с
 * информацией о собаках и их владельцах.
 */
@RestController
@RequestMapping("dogs")
public class DogController {
    private final DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Вносим информацию о новой собаке " +
                            "пример вносимой информации:.......... ",
                    content = @Content(
                            schema = @Schema(implementation = Dog[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @PostMapping //POST http://localhost:8080/dogs
    public Dog createDog(@RequestBody Dog dog) {
        return dogService.createDod(dog);
    }
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Изменяе ранее внесенную информацию о собаке ",
                    content = @Content(
                            schema = @Schema(implementation = Dog[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @PutMapping //PUT http://localhost:8080/dogs
    public Dog editDog(@RequestBody Dog dog) {
        return dogService.editDog(dog);
    }
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Удаляем ранее внесенную информацию о собаке ",
                    content = @Content(
                            schema = @Schema(implementation = Dog[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @DeleteMapping("{id}") //DELETE http://localhost:8080/dogs/3
    public ResponseEntity deleteDog(@PathVariable Long id) {
        dogService.deleteDog(id);
        return ResponseEntity.ok().build();
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Поиск собак по кличке, породе , и вывод списка всех собак ",
                    content = @Content(
                            schema = @Schema(implementation = Dog[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @GetMapping //GET http://localhost:8080/dogs
    public ResponseEntity findDog(@Parameter(description =
            "Кличка собаки, часть клички, прописными или заглавными буквами",
            example = "пример заполнение: дЖЕк")@RequestParam(required = false, name = "Кличка питомца") String nameDog,
                                  @RequestParam(required = false, name = "Порода собаки, к примеру РОТвейлеР") String breedDog) {
        if (nameDog != null && !nameDog.isBlank()) {
            return ResponseEntity.ok(dogService.findDodByNameDog(nameDog));
        }
        if (breedDog != null && !breedDog.isBlank()) {
            return ResponseEntity.ok(dogService.findDodByBreed(breedDog));
        }
        return ResponseEntity.ok(dogService.getAllDogs());
    }

}