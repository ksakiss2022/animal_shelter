package com.example.animal_shelter.animal_shelter.repository;

import com.example.animal_shelter.animal_shelter.model.LocationMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Optional;
@Repository
public interface LocationMapRepository extends JpaRepository<LocationMap, Long> {
  //  Optional<LocationMap> findLocationMapById(Long locationMapId);
    Optional<LocationMap> findLocationMapByShelterId(Long shelterId);


}
