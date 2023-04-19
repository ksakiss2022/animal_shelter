package com.example.animal_shelter.animal_shelter.model;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс для создания объекта DocumentCat. Хранит в себе необходимые свойства данного объекта
 * ().
 * Реализована инкапсуляция и переопределены методы equals() и hashCode().
 */
@Entity
@Table(name = "document_cat")
@TypeDef(
        name = "types_documents",
        typeClass  = PostgreSQLEnumType.class
)

public class DocumentCat {
    @Id
    @Type(type = "types_documents")
    @Column(name = "type_document_cat", nullable = false, columnDefinition = "types_document_cat")
    @Enumerated(EnumType.STRING)
    private TypesDocuments typeDocumentCat ;

    @Column(name = "text", nullable = false)
    private String text;

    public DocumentCat() {
    }

    public DocumentCat(TypesDocuments typeDocumentCat, String text) {
        this.typeDocumentCat = typeDocumentCat;
        this.text = text;
    }

    public TypesDocuments getTypeDocumentCat() {
        return typeDocumentCat;
    }

    public void setTypeDocumentCat(TypesDocuments typeDocumentCat) {
        this.typeDocumentCat = typeDocumentCat;
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
        DocumentCat that = (DocumentCat) o;
        return typeDocumentCat == that.typeDocumentCat && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeDocumentCat, text);
    }

    @Override
    public String toString() {
        return "DocumentCat{" +
                "typeDocumentCat=" + typeDocumentCat +
                ", text='" + text + '\'' +
                '}';
    }

}
