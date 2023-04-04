package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.Cat;
import com.example.animal_shelter.animal_shelter.model.Dog;
import com.example.animal_shelter.animal_shelter.service.CatService;
import com.example.animal_shelter.animal_shelter.service.DogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Контроллер DogController предназначен для вывода дынных собак
 * с использованием методов прописанных в DogService.
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

    @PostMapping //POST http://localhost:8080/dogs
    public Dog createDog(@RequestBody Dog dog) {
        return dogService.createDod(dog);
    }

    @PutMapping //PUT http://localhost:8080/dogs
    public Dog editDog(@RequestBody Dog dog) {
        return dogService.editDog(dog);
    }

    @DeleteMapping("{id}") //DELETE http://localhost:8080/dogs/3
    public ResponseEntity deleteDog(@PathVariable Long id) {
        dogService.deleteDog(id);
        return ResponseEntity.ok().build();
    }

}
