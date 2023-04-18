package com.example.animal_shelter.animal_shelter.repository;

import com.example.animal_shelter.animal_shelter.model.DocumentDog;
import com.example.animal_shelter.animal_shelter.model.TypesDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentDogRepository extends JpaRepository<DocumentDog, Long> {
    void deleteDocumentDogByTypeDocumentDog(TypesDocuments typeDocumentDog);
    DocumentDog findDocumentDogByTypeDocumentDog(TypesDocuments typeDocumentDog);
}
