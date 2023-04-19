package com.example.animal_shelter.animal_shelter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * Класс для создания отчётов. Хранит в себе необходимые характеристики для заполнения
 * (фото животного, рацион животного, общее самочувствие, изменение привычек).
 * Реализована инкапсуляция, переопределены методы equals(), hashCode() и toString().
 */
@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ration", nullable = false)
    private String ration;
    @Column(name = "health", nullable = false)
    private String health;
    @Column(name = "habits", nullable = false)
    private String habits;
    @Column(name = "days", nullable = false)
    private long days;
    @Column(name = "file_path", nullable = false)
    private String filePath;
    @Column(name = "file_size", nullable = false)
    private long fileSize;

    @JsonIgnore
    private byte[] data;
    @Column(name = "caption", nullable = false)
    private String caption;

    @Column(name = "last_message", nullable = false)
    private Date lastMessage;
    @Column(name = "last_message_ms", nullable = false)
    private Long lastMessageMs;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

//    @Column(name = "person_id", nullable = false)
//    private Long personId;



    public Report() {

    }

    public Report(Long chatId, byte[] data) {
        this.chatId = chatId;
        this.data = data;
    }

    public Report(String ration, String health, String habits) {
        this.ration = ration;
        this.health = health;
        this.habits = habits;
    }

    public Report(Long chatId, byte[] data,
                  String ration, String health, String habits) {
        this.chatId = chatId;
        this.data = data;
        this.ration = ration;
        this.health = health;
        this.habits = habits;
    }

    public Long getLastMessageMs() {
        return lastMessageMs;
    }

    public void setLastMessageMs(Long lastMessageMs) {
        this.lastMessageMs = lastMessageMs;
    }

    public Date getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Date lastMessage) {
        this.lastMessage = lastMessage;
    }

    public long getId() {
        return id;
    }

//    public Long getPersonId() {
//        return personId;
//    }
//
//    public void setPersonId(Long personId) {
//        this.personId = personId;
//    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report that = (Report) o;
        return id == that.id && Objects.equals(ration, that.ration) && Objects.equals(health, that.health) && Objects.equals(habits, that.habits) && Objects.equals(days, that.days);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ration, health, habits, days);
    }

    public String getRation() {
        return ration;
    }

    public void setRation(String ration) {
        this.ration = ration;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getHabits() {
        return habits;
    }

    public void setHabits(String habits) {
        this.habits = habits;
    }

    public long getDays() {
        return days;
    }

    public void setDays(Long days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", ration='" + ration + '\'' +
                ", health='" + health + '\'' +
                ", habits='" + habits + '\'' +
                ", days=" + days +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", data=" + Arrays.toString(data) +
                ", caption='" + caption + '\'' +
                ", lastMessage=" + lastMessage +
                ", lastMessageMs=" + lastMessageMs +
//                ", personId=" + personId +
                '}';
    }
}
