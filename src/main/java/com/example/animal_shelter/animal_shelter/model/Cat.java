
package com.example.animal_shelter.animal_shelter.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс для создания объекта Cat. Хранит в себе необходимые свойства данного объекта
 * (порода, имя, год рождения, краткое описание).
 * Реализована инкапсуляция и переопределены методы equals() и hashCode().
 */
@Entity
@Table(name = "cat")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Первичный ключ. Поле для организации связи с PersonCat
    @Column(nullable = false)
    private String breed;
    @Column(name = "name_cat", nullable = false)
    private String nameCat;
    @Column(name = "year_of_birth", nullable = false)
    private int yearOfBirthCat;
    @Column(name = "description", nullable = false)
    private String description;

    public Cat() {

    }

    public Cat(Long id, String breed, String nameCat, int yearOfBirthCat, String description) {
        this.id = id;
        this.breed = breed;
        this.nameCat = nameCat;
        this.yearOfBirthCat = yearOfBirthCat;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getNameCat() {
        return nameCat;
    }

    public void setNameCat(String nameCat) {
        this.nameCat = nameCat;
    }

    public int getYearOfBirthCat() {
        return yearOfBirthCat;
    }

    public void setYearOfBirthCat(int yearOfBirthCat) {
        this.yearOfBirthCat = yearOfBirthCat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return yearOfBirthCat == cat.yearOfBirthCat && id.equals(cat.id) && breed.equals(cat.breed) && nameCat.equals(cat.nameCat) && description.equals(cat.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, breed, nameCat, yearOfBirthCat, description);
    }

}