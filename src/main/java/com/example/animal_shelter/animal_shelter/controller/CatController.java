package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.Cat;
import com.example.animal_shelter.animal_shelter.service.CatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Контроллер CatController предназначен для вывода дынных кошек
 * с использованием методов прописанных в CatService.
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

    @PostMapping //POST http://localhost:8080/cats
    public Cat createCat(@RequestBody Cat cat) {
        return catService.createCat(cat);
    }

    @PutMapping //PUT http://localhost:8080/cats
    public Cat editCat(@RequestBody Cat cat) {
        return catService.editCat(cat);
    }

    @DeleteMapping("{id}") //DELETE http://localhost:8080/cats/3
    public ResponseEntity deleteCat(@PathVariable Long id) {
        catService.deleteCat(id);
        return ResponseEntity.ok().build();
    }

}
