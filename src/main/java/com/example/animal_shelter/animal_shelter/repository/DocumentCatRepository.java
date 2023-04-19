package com.example.animal_shelter.animal_shelter.repository;

import com.example.animal_shelter.animal_shelter.model.DocumentCat;
import com.example.animal_shelter.animal_shelter.model.TypesDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentCatRepository extends JpaRepository<DocumentCat, Long> {
    void deleteDocumentCatByTypeDocumentCat(TypesDocuments typeDocumentCat);
    DocumentCat findDocumentCatByTypeDocumentCat(TypesDocuments typeDocumentCat);

}
