package com.example.animal_shelter.animal_shelter.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
//@Getter
//@Setter
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
@Table(name = "cat_database")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String breed;
    @Column(name = "name_cat", nullable = false)
    private String nameCat;
    @Column(name = "year_of_birth_cat", nullable = false)
    private int yearOfBirthCat;
    @Column(name = "description", nullable = false)
    private String description;

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

<<<<<<< HEAD
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
=======
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
>>>>>>> origin/feature-svetlana
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
<<<<<<< HEAD

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", breed='" + breed + '\'' +
                ", name='" + name + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", description='" + description + '\'' +
                '}';
    }

=======
>>>>>>> origin/feature-svetlana
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
<<<<<<< HEAD
        return id == cat.id && yearOfBirth == cat.yearOfBirth && Objects.equals(breed, cat.breed) && Objects.equals(name, cat.name);
=======
        return yearOfBirthCat == cat.yearOfBirthCat && id.equals(cat.id) && breed.equals(cat.breed) && nameCat.equals(cat.nameCat) && description.equals(cat.description);
>>>>>>> origin/feature-svetlana
    }

    @Override
    public int hashCode() {
<<<<<<< HEAD
        return Objects.hash(id, breed, name, yearOfBirth);
=======
        return Objects.hash(id, breed, nameCat, yearOfBirthCat, description);
>>>>>>> origin/feature-svetlana
    }

}
