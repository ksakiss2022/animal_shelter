package com.example.animal_shelter.animal_shelter.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс для создания объекта Dog. Хранит в себе необходимые свойства данного объекта
 * (порода, имя, год рождения, краткое описание).
 * Реализована инкапсуляция и переопределены методы equals() и hashCode().
 */
@Entity
@Table(name = "dog")
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Первичный ключ. Поле для организации связи с personDog
    @Column(name = "breed", nullable = false)
    private String breedDog;
    @Column(name = "name_dog", nullable = false)
    private String nameDog;
    @Column(name = "year_of_birth_dog", nullable = false)
    private int yearOfBirthDog;

    @Column(name = "description", nullable = false)
    private String description;

    public Dog() {

    }

    public Dog(Long id, String breedDog, String nameDog, int yearOfBirthDog, String description) {
        this.id = id;
        this.breedDog = breedDog;
        this.nameDog = nameDog;
        this.yearOfBirthDog = yearOfBirthDog;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBreedDog() {
        return breedDog;
    }

    public void setBreedDog(String breedDog) {
        this.breedDog = breedDog;
    }


    public String getNameDog() {
        return nameDog;
    }

    public void setNameDog(String nameDog) {
        this.nameDog = nameDog;
    }

    public int getYearOfBirthDog() {
        return yearOfBirthDog;
    }

    public void setYearOfBirthDog(int yearOfBirthDog) {
        this.yearOfBirthDog = yearOfBirthDog;
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
        Dog dog = (Dog) o;
        return yearOfBirthDog == dog.yearOfBirthDog && id.equals(dog.id) && breedDog.equals(dog.breedDog) &&  nameDog.equals(dog.nameDog) && description.equals(dog.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, breedDog, nameDog, yearOfBirthDog, description);
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", breedDog='" + breedDog + '\'' +
                ", nameDog='" + nameDog + '\'' +
                ", yearOfBirthDog=" + yearOfBirthDog +
                ", description='" + description + '\'' +
                '}';
    }
}