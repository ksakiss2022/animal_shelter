package com.example.animal_shelter.animal_shelter.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс для создания объекта PersonCat. Хранит в себе необходимые свойства данного объекта
 * (имя, телефон, год рождения, электронная почта, адрес, id пользователя).
 * Реализована инкапсуляция и переопределены методы equals() и hashCode().
 */
@Entity
@Table(name = "person_cat")
public class PersonCat {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //поле для организации связи с report
    @Column(nullable = false)
    private String name;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "year_of_birth", nullable = false)
    private int yearOfBirth;
    @Column(name = "mail", nullable = false)
    private String mail;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id")
    private Cat cat;


    @OneToOne(orphanRemoval = true)
    @JoinTable(name = "person_cat_report",
            joinColumns = @JoinColumn(name = "person_cat"),
            inverseJoinColumns = @JoinColumn(name = "report_id"))
    private Report report;

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public PersonCat() {
    }

    public PersonCat(String name, String phone, Long chatId) {
        this.name = name;
        this.phone = phone;
        this.chatId = chatId;
    }

    public PersonCat(Long id, String name, int yearOfBirth, String phone, String mail, String address, Long chatId) {
        this.id = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
        this.chatId = chatId;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonCat personCat = (PersonCat) o;
        return yearOfBirth == personCat.yearOfBirth && Objects.equals(id, personCat.id) && Objects.equals(name, personCat.name) && Objects.equals(phone, personCat.phone) && Objects.equals(mail, personCat.mail) && Objects.equals(address, personCat.address) && Objects.equals(chatId, personCat.chatId) && Objects.equals(cat, personCat.cat) && Objects.equals(report, personCat.report);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, yearOfBirth, mail, address, chatId, cat, report);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", address='" + address + '\'' +
                ", chatId=" + chatId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


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
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getChatId() {
        return chatId;
    }
}
