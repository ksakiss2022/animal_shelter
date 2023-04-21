package com.example.animal_shelter.animal_shelter.model;

import javax.persistence.*;

@Entity
@Table(name = "volunteer")
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "contact_info", nullable = false)
    private String contactInfo;


    public Volunteer(String name, String phone, String contactInfo) {
        this.name = name;
        this.phone = phone;
        this.contactInfo = contactInfo;
    }

    public Volunteer() {

    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
