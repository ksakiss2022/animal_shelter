package com.example.animal_shelter.animal_shelter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс для создания объекта Shelter. Хранит в себе необходимые свойства данного объекта
 * (информация, расписание, адрес, схема проезда, рекомендации по технике безопасности).
 * Реализована инкапсуляция и переопределены методы equals() и hashCode().
 */
@Entity
@Table(name = "shelter")
@TypeDef(
        name = "types_shelters",
        typeClass = PostgreSQLEnumType.class
)
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Первичный ключ.

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String information;

    @Column(nullable = false)
    private String schedule;

    @Column(nullable = false)
    private String address;

    @Column(name = "safety_recommendations", nullable = false)
    private String safetyRecommendations;


    @Type(type = "types_shelters")
    @Column(name = "type_shelter", nullable = false, columnDefinition = "type_shelter")
    @Enumerated(EnumType.STRING)
    private TypesShelters typeShelter;

    @Column(name = "cynologists")
    private String cynologists;

    @Column(name = "cynologists_advice")
    private String cynologistsAdvice;



    public Shelter() {

    }

    public Shelter(Long id, String name, String information, String schedule, String address, String safetyRecommendations, TypesShelters typeShelter) {
        this.id = id;
        this.name = name;
        this.information = information;
        this.schedule = schedule;
        this.address = address;
        this.safetyRecommendations = safetyRecommendations;
        this.typeShelter = typeShelter;
    }

    public Shelter(String name, String information, String schedule, String address, String safetyRecommendations, TypesShelters typeShelter) {
        this.name = name;
        this.information = information;
        this.schedule = schedule;
        this.address = address;
        this.safetyRecommendations = safetyRecommendations;
        this.typeShelter = typeShelter;
    }

    public Shelter(Long id, String name, String information, String schedule, String address, String safetyRecommendations, TypesShelters typeShelter, String cynologists, String cynologistsAdvice) {
        this.id = id;
        this.name = name;
        this.information = information;
        this.schedule = schedule;
        this.address = address;
        this.safetyRecommendations = safetyRecommendations;
        this.typeShelter = typeShelter;
        this.cynologists = cynologists;
        this.cynologistsAdvice = cynologistsAdvice;
    }

    public Shelter(String name, String information, String schedule, String address, String safetyRecommendations, TypesShelters typeShelter, String cynologists, String ccynologistsAdvice) {
        this.name = name;
        this.information = information;
        this.schedule = schedule;
        this.address = address;
        this.safetyRecommendations = safetyRecommendations;
        this.typeShelter = typeShelter;
        this.cynologists = cynologists;
        this.cynologistsAdvice = cynologistsAdvice;
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", information='" + information + '\'' +
                ", schedule='" + schedule + '\'' +
                ", address='" + address + '\'' +
                ", safetyRecommendations='" + safetyRecommendations + '\'' +
                ", typeShelter=" + typeShelter +
                ", cynologists='" + cynologists + '\'' +
                ", cynologistsAdvice='" + cynologistsAdvice + '\'' +
                '}';
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getSafetyRecommendations() {
        return safetyRecommendations;
    }

    public void setSafetyRecommendations(String safetyRecommendations) {
        this.safetyRecommendations = safetyRecommendations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypesShelters getTypeShelter() {
        return typeShelter;
    }

    public void setTypeShelter(TypesShelters typeShelter) {
        this.typeShelter = typeShelter;
    }


    public String getCynologists() {
        return cynologists;
    }

    public void setCynologists(String cynologists) {
        this.cynologists = cynologists;
    }

    public String getCynologistsAdvice() {
        return cynologistsAdvice;
    }

    public void setCynologistsAdvice(String cynologistsAdvice) {
        this.cynologistsAdvice = cynologistsAdvice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return Objects.equals(id, shelter.id) && Objects.equals(name, shelter.name) && Objects.equals(information, shelter.information) && Objects.equals(schedule, shelter.schedule) && Objects.equals(address, shelter.address) && Objects.equals(safetyRecommendations, shelter.safetyRecommendations) && typeShelter == shelter.typeShelter && Objects.equals(cynologists, shelter.cynologists) && Objects.equals(cynologistsAdvice, shelter.cynologistsAdvice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, information, schedule, address, safetyRecommendations, typeShelter, cynologists, cynologistsAdvice);
    }
}
