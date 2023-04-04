package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.PersonCat;
import com.example.animal_shelter.animal_shelter.model.PersonDog;
import com.example.animal_shelter.animal_shelter.service.PersonCatService;
import com.example.animal_shelter.animal_shelter.service.PersonDogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Контроллер PersonCatController предназначен для вывода дынных о владельцах и потенциальных владельцах кошек
 * с использованием методов прописанных в PersonCatService.
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

    @PostMapping //POST http://localhost:8080/person_cats
    public PersonCat createPersonCat(@RequestBody PersonCat personCat) {
        return personCatService.createPersonCat(personCat);
    }

    @PutMapping //PUT http://localhost:8080/person_cats
    public PersonCat editPersonCat(@RequestBody PersonCat personCat) {
        return personCatService.editPersonCat(personCat);
    }

    @DeleteMapping("{id}") //DELETE http://localhost:8080/person_cats/3
    public ResponseEntity deletePersonCat(@PathVariable Long id) {
        personCatService.deletePersonCat(id);
        return ResponseEntity.ok().build();
    }
}
