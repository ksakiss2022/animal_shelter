package com.example.animal_shelter.animal_shelter.repository;
import com.example.animal_shelter.animal_shelter.model.Shelter;
import com.example.animal_shelter.animal_shelter.model.TypesShelters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    Collection<Shelter> findSheltersByNameIgnoreCase(String name);
    Shelter findSheltersByTypeShelter(TypesShelters typesShelters);

}
