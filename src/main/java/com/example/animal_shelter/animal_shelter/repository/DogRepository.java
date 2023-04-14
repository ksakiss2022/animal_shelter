package com.example.animal_shelter.animal_shelter.repository;

import com.example.animal_shelter.animal_shelter.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    Collection<Dog> findDogByBreedDogContainsIgnoreCase(String breedDog);

    Dog findDogByNameDogContainsIgnoreCase(String nameDog);

}
