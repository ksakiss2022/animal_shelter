package com.example.animal_shelter.animal_shelter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс для создания объекта Shelter. Хранит в себе необходимые свойства данного объекта
 * (информация, расписание, адрес, схема проезда, рекомендации по технике безопасности).
 * Реализована инкапсуляция и переопределены методы equals() и hashCode().
 */
@Entity
@Table(name = "shelter")
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

    public Shelter() {

    }

    public Shelter(Long id, String name, String information, String schedule, String address, String safetyRecommendations) {
        this.id = id;
        this.name = name;
        this.information = information;
        this.schedule = schedule;
        this.address = address;
        this.safetyRecommendations = safetyRecommendations;
    }

    public Shelter(String name, String information, String schedule, String address, String safetyRecommendations) {
        this.name = name;
        this.information = information;
        this.schedule = schedule;
        this.address = address;
        this.safetyRecommendations = safetyRecommendations;
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
}
