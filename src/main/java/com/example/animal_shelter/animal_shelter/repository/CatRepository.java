package com.example.animal_shelter.animal_shelter.repository;

import com.example.animal_shelter.animal_shelter.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    Collection<Cat> findCatByBreedContainsIgnoreCase(String breed);
    Cat findCatByNameCatContainsIgnoreCase(String nameCat);

}
