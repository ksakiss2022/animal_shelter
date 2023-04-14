package com.example.animal_shelter.animal_shelter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Класс для создания объекта BotUser. Хранит в себе необходимые свойства данного объекта
 * (id, контактные данные, последний запрос, выбранный приют).
 * Реализована инкапсуляция и переопределены методы equals() и hashCode().
 */
@Entity
@Table(name = "bot_user")
public class BotUser {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Первичный ключ.

    @Column(name = "last_request")
    private String lastRequest;

    @Column()
    private String contact;

    @Column(name = "shelter_id", nullable = false)
    private Long shelterId;

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

    @Override
    public String toString() {
        return "BotUser{" +
                "id='" + id + '\'' +
                ", lastRequest='" + lastRequest + '\'' +
                ", contact='" + contact + '\'' +
                ", shelterId=" + shelterId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotUser botUser = (BotUser) o;
        return Objects.equals(id, botUser.id) && Objects.equals(lastRequest, botUser.lastRequest) && Objects.equals(contact, botUser.contact) && Objects.equals(shelterId, botUser.shelterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastRequest, contact, shelterId);
    }
}
