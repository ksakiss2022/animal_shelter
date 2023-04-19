package com.example.animal_shelter.animal_shelter.model;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс для создания объекта BotUser. Хранит в себе необходимые свойства данного объекта
 * (id, контактные данные, последний запрос, выбранный приют).
 * Реализована инкапсуляция и переопределены методы equals() и hashCode().
 */
@Entity
@Table(name = "bot_user")
@TypeDef(
        name = "types_shelters",
        typeClass  = PostgreSQLEnumType.class
)


public class BotUser {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Первичный ключ.

    @Column(name = "last_request")
    private String lastRequest;

    @Column()
    private String contact;

    @Column(name = "shelter_id")
    private Long shelterId;

    @Type(type = "types_shelters")
    @Column(name = "type_shelter", columnDefinition = "type_shelter")
    @Enumerated(EnumType.STRING)
    private TypesShelters typeShelter;


    public BotUser() {
    }

    public BotUser(Long id, TypesShelters typeShelter) {
        this.id = id;
        this.typeShelter = typeShelter;
    }

    public BotUser(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastRequest() {
        return lastRequest;
    }

    public void setLastRequest(String lastRequest) {
        this.lastRequest = lastRequest;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Long getShelterId() {
        return shelterId;
    }

    public void setShelterId(Long shelterId) {
        this.shelterId = shelterId;
    }

    public TypesShelters getTypeShelter() {
        return typeShelter;
    }

    public void setTypeShelter(TypesShelters typeShelter) {
        this.typeShelter = typeShelter;
    }

    @Override
    public String toString() {
        return "BotUser{" +
                "id='" + id + '\'' +
                ", lastRequest='" + lastRequest + '\'' +
                ", contact='" + contact + '\'' +
                ", shelterId=" + shelterId +
                ", typeShelter=" + typeShelter +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotUser botUser = (BotUser) o;
        return Objects.equals(id, botUser.id) && Objects.equals(lastRequest, botUser.lastRequest) && Objects.equals(contact, botUser.contact) && Objects.equals(shelterId, botUser.shelterId) && typeShelter == botUser.typeShelter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastRequest, contact, shelterId, typeShelter);
    }
}
