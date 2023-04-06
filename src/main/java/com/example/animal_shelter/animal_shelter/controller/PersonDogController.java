package com.example.animal_shelter.animal_shelter.controller;


import com.example.animal_shelter.animal_shelter.model.PersonDog;
import com.example.animal_shelter.animal_shelter.service.PersonDogService;
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

    @Operation(
            summary = "Вносим информацию о новом владельце собаки.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новый владельц собаки.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PersonDog.class))
                            )
                    )
            },
            tags = "Person Dog"
    )
    @PostMapping //POST http://localhost:8080/person_dogs
    public PersonDog createPersonDog(@RequestBody PersonDog personDog) {
        return personDogService.createPersonDog(personDog);
    }

    @Operation(
            summary = "Изменяем ранее внесенную информацию о владельце собаки.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененная информация о владельце собаки. ",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PersonDog.class))
                            )
                    )
            },
            tags = "Person Dog"
    )
    @PutMapping //PUT http://localhost:8080/person_dogs
    public PersonDog editPersonDog(@RequestBody PersonDog personDog) {
        return personDogService.editPersonDog(personDog);
    }

    @Operation(
            summary = "Удаляем ранее внесенную информацию о владельце собаки.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленная информацию о владельце собаки.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PersonDog.class))
                            )
                    )
            },
            tags = "Person Dog"
    )
    @DeleteMapping("{id}") //DELETE http://localhost:8080/person_dogs/3
    public ResponseEntity deletePersonDog(@PathVariable Long id) {
        personDogService.deletePersonDog(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Поиск хозяев собак по имени хозяина, @mail, вывод списка всех хозяев.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденные хозяева собак.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PersonDog.class))
                            )
                    )
            },
            tags = "Person Dog"
    )
    @GetMapping //GET http://localhost:8080/person_dogs
    public ResponseEntity findPersonsDogs(@Parameter(description =
            "Имя хозяина собаки, часть имени, прописными или заглавными буквами",
            example = "пример заполнение: Короленко") @RequestParam(required = false, name = "Имя хозяина питомца") String name,
                                          @RequestParam(required = false, name = "@mail, к примеру vaska@mail.ru") String mail) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(personDogService.findPersonsDogByNamePersons(name));
        }
        if (mail != null && !mail.isBlank()) {
            return ResponseEntity.ok(personDogService.findPersonsDogByMail(mail));
        }
        return ResponseEntity.ok(personDogService.getAllParsonsDogs());
    }
}