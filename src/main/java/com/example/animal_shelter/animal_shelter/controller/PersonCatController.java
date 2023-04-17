package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.PersonCat;
import com.example.animal_shelter.animal_shelter.service.PersonCatService;
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
 * <b>Контроллер PersonCatController предназначен для вывода дынных о владельцах и потенциальных владельцах кошек
 * с использованием методов прописанных в PersonCatService.</b>
 * Реализованы POST, PUT, DELETE запросы.
 * В дальнейшем будут добавлено множество других удобных запросов, для работы с информацией о кошках и их владельцах.
 */
@RestController
@RequestMapping("person_cats")
public class PersonCatController {
    private final PersonCatService personCatService;

    public PersonCatController(PersonCatService personCatService) {
        this.personCatService = personCatService;
    }

    @Operation(
            summary = "Вносим информацию о новом владельце кошки.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Внесенная информацию о новом владельце кошки ",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PersonCat.class))
                            )
                    )
            },
            tags = "Person Cat"
    )
    @PostMapping //POST http://localhost:8080/person_cats
    public PersonCat createPersonCat(@RequestBody PersonCat personCat) {
        return personCatService.createPersonCat(personCat);
    }

    @Operation(
            summary = "Изменяе ранее внесенную информацию о владельце кошки.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Изменная информацию о владельце кошки ",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PersonCat.class))
                            )
                    )
            },
            tags = "Person Cat"
    )
    @PutMapping //PUT http://localhost:8080/person_cats
    public PersonCat editPersonCat(@RequestBody PersonCat personCat) {
        return personCatService.editPersonCat(personCat);
    }

    @Operation(
            summary = "Удаляем ранее внесенную информацию о владельце кошки.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленная информация о владельце кошки ",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PersonCat.class))
                            )
                    )
            },
            tags = "Person Cat"
    )
    @DeleteMapping("{id}") //DELETE http://localhost:8080/person_cats/3
    public ResponseEntity<Void> deletePersonCat(@PathVariable Long id) {
        personCatService.deletePersonCat(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Поиск хозяев кошкек по имени хозяина, @mail, вывод списка всех хозяев.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный хозяин кошки.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PersonCat.class))
                            )
                    )
            },
            tags = "Person Cat"
    )
    @GetMapping //GET http://localhost:8080/person_cats
    public ResponseEntity<?> findPersonsCats(@Parameter(description =
            "Имя хозяина кошки, часть имени, прописными или заглавными буквами",
            example = "пример заполнение: Короленко") @RequestParam(required = false, name = "Имя хозяина питомца") String name,
                                          @RequestParam(required = false, name = "@mail, к примеру vaska@mail.ru") String mail) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(personCatService.findPersonsCatByNamePersons(name));
        }
        if (mail != null && !mail.isBlank()) {
            return ResponseEntity.ok(personCatService.findPersonsCatByMail(mail));
        }
        return ResponseEntity.ok(personCatService.getAllParsonsCats());
    }
}