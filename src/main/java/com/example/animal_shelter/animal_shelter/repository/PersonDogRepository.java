package com.example.animal_shelter.animal_shelter.repository;

import com.example.animal_shelter.animal_shelter.model.PersonDog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PersonDogRepository extends JpaRepository<PersonDog, Long> {
    Collection<PersonDog> findPersonDogByNameContainsIgnoreCase(String name);

    PersonDog findPersonDogByMailContainsIgnoreCase(String mail);
}
