package com.example.animal_shelter.animal_shelter.model;

//import org.hibernate.annotations.Type;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Objects;



/**
 * Класс для создания объекта DocumentDog. Хранит в себе необходимые свойства данного объекта
 * ().
 * Реализована инкапсуляция и переопределены методы equals() и hashCode().
 */
@Entity
@Table(name = "document_dog")
@TypeDef(
        name = "types_documents",
        typeClass  = PostgreSQLEnumType.class
)

public class DocumentDog {
    @Id
    @Type(type = "types_documents")
    @Column(name = "type_document_dog", nullable = false, columnDefinition = "types_document_dog")
    @Enumerated(EnumType.STRING)
    private TypesDocuments typeDocumentDog;

    @Column(name = "text", nullable = false)
    private String text;

    public DocumentDog() {
    }

    public DocumentDog (TypesDocuments typeDocumentDog, String text ){
        this.typeDocumentDog = typeDocumentDog;
        this.text = text;
    }



    public TypesDocuments getTypeDocumentDog() {
        return typeDocumentDog;
    }

    public void setTypeDocumentDog(TypesDocuments typeDocumentDog) {
        this.typeDocumentDog = typeDocumentDog;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentDog that = (DocumentDog) o;
        return typeDocumentDog == that.typeDocumentDog && Objects.equals(text, that.text) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash( typeDocumentDog, text);
    }

    @Override
    public String toString() {
        return "DocumentDog{" +
                ", typeDocumentDog=" + typeDocumentDog +
                ", text='" + text + '\'' +
                '}';
    }
}
