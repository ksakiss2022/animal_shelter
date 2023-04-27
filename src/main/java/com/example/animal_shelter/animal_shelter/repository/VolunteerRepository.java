package com.example.animal_shelter.animal_shelter.repository;

import com.example.animal_shelter.animal_shelter.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Collection<Volunteer> findVolunteerBySpatializationContainingIgnoreCase (String name);

    Volunteer findVolunteerByNameContainingIgnoreCase(String spatialization);
}
