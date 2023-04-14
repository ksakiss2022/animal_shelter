package com.example.animal_shelter.animal_shelter.repository;

import com.example.animal_shelter.animal_shelter.model.PersonCat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PersonCatRepository extends JpaRepository<PersonCat, Long> {
    Collection<PersonCat> findPersonCatByNameContainsIgnoreCase(String name);

    PersonCat findPersonCatByMailContainsIgnoreCase(String mail);
}
