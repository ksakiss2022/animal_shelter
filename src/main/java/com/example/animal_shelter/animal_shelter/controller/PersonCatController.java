package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.PersonCat;
import com.example.animal_shelter.animal_shelter.service.PersonCatService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Вносим информацию о новом владельце кошки " +
                            "пример вносимой информации:.......... ",
                    content = @Content(
                            schema = @Schema(implementation = PersonCat[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @PostMapping //POST http://localhost:8080/person_cats
    public PersonCat createPersonCat(@RequestBody PersonCat personCat) {
        return personCatService.createPersonCat(personCat);
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Изменяе ранее внесенную информацию о владельце кошки ",
                    content = @Content(
                            schema = @Schema(implementation = PersonCat[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @PutMapping //PUT http://localhost:8080/person_cats
    public PersonCat editPersonCat(@RequestBody PersonCat personCat) {
        return personCatService.editPersonCat(personCat);
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Удаляем ранее внесенную информацию о владельце кошки ",
                    content = @Content(
                            schema = @Schema(implementation = PersonCat[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @DeleteMapping("{id}") //DELETE http://localhost:8080/person_cats/3
    public ResponseEntity deletePersonCat(@PathVariable Long id) {
        personCatService.deletePersonCat(id);
        return ResponseEntity.ok().build();
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Поиск хозяев кошкек по имени хозяина, @mail, вывод списка всех хозяев",
                    content = @Content(
                            schema = @Schema(implementation = PersonCat[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @GetMapping //GET http://localhost:8080/person_cats
    public ResponseEntity findPersonsCats(@Parameter(description =
            "Имя хозяина кошки, часть имени, прописными или заглавными буквами",
            example = "пример заполнение: Короленко")@RequestParam(required = false, name = "Имя хозяина питомца") String name,
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