package com.example.animal_shelter.animal_shelter.controller;


import com.example.animal_shelter.animal_shelter.model.PersonDog;
import com.example.animal_shelter.animal_shelter.service.PersonDogService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <b>Контроллер PersonDogController предназначен для вывода дынных о владельцах и потенциальных владельцах собак
 * с использованием методов прописанных в PersonDogService.</b>
 * Реализованы POST, PUT, DELETE запросы.
 * В дальнейшем будут добавлено множество других удобных запросов, для работы с информацией о Собаках и их владельцах.
 */
@RestController
@RequestMapping("person_dogs")
public class PersonDogController {
    private final PersonDogService personDogService;

    public PersonDogController(PersonDogService personDogService) {
        this.personDogService = personDogService;
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Вносим информацию о новом владельце собаки " +
                            "пример вносимой информации:.......... ",
                    content = @Content(
                            schema = @Schema(implementation = PersonDog[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @PostMapping //POST http://localhost:8080/person_dogs
    public PersonDog createPersonDog(@RequestBody PersonDog personDog) {
        return personDogService.createPersonDog(personDog);
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Изменяе ранее внесенную информацию о владельце собаки ",
                    content = @Content(
                            schema = @Schema(implementation = PersonDog[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @PutMapping //PUT http://localhost:8080/person_dogs
    public PersonDog editPersonDog(@RequestBody PersonDog personDog) {
        return personDogService.editPersonDog(personDog);
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Удаляем ранее внесенную информацию о владельце собаки ",
                    content = @Content(
                            schema = @Schema(implementation = PersonDog[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @DeleteMapping("{id}") //DELETE http://localhost:8080/person_dogs/3
    public ResponseEntity deletePersonDog(@PathVariable Long id) {
        personDogService.deletePersonDog(id);
        return ResponseEntity.ok().build();
    }
}