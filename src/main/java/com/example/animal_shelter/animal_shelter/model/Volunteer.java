package com.example.animal_shelter.animal_shelter.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "volunteer")
public class Volunteer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Первичный ключ. Поле для организации связи с PersonCat
    @Column(nullable = false)
    private String spatialization;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "work_experience", nullable = false)
    private int workExperience;
    @Column(name = "description", nullable = false)
    private String description;



    public Volunteer(String spatialization, String name, int workExperience, String description) {
        this.spatialization = spatialization;
        this.name = name;
        this.workExperience = workExperience;
        this.description = description;
    }

    public Volunteer(Long id, String spatialization, String name, int workExperience, String description) {
        this.id = id;
        this.spatialization = spatialization;
        this.name = name;
        this.workExperience = workExperience;
        this.description = description;
    }

    public Volunteer() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpatialization() {
        return spatialization;
    }

    public void setSpatialization(String spatialization) {
        this.spatialization = spatialization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(int workExperience) {
        this.workExperience = workExperience;
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
        Volunteer volunteer = (Volunteer) o;
        return workExperience == volunteer.workExperience && id.equals(volunteer.id) && spatialization.equals(volunteer.spatialization) && name.equals(volunteer.name) && description.equals(volunteer.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, spatialization, name, workExperience, description);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", spatialization='" + spatialization + '\'' +
                ", name='" + name + '\'' +
                ", workExperience=" + workExperience +
                ", description='" + description + '\'' +
                '}';
    }

}
