package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.Dog;
import com.example.animal_shelter.animal_shelter.model.PersonDog;
import com.example.animal_shelter.animal_shelter.service.DogService;
import com.example.animal_shelter.animal_shelter.service.PersonDogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Контроллер PersonDogController предназначен для вывода дынных о владельцах и потенциальных владельцах собак
 * с использованием методов прописанных в PersonDogService.
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

    @PostMapping //POST http://localhost:8080/person_dogs
    public PersonDog createPersonDog(@RequestBody PersonDog personDog) {
        return personDogService.createPersonDog(personDog);
    }

    @PutMapping //PUT http://localhost:8080/person_dogs
    public PersonDog editPersonDog(@RequestBody PersonDog personDog) {
        return personDogService.editPersonDog(personDog);
    }

    @DeleteMapping("{id}") //DELETE http://localhost:8080/person_dogs/3
    public ResponseEntity deletePersonDog(@PathVariable Long id) {
        personDogService.deletePersonDog(id);
        return ResponseEntity.ok().build();
    }
}
