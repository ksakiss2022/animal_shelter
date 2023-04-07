package com.example.animal_shelter.animal_shelter.model;

import com.example.animal_shelter.animal_shelter.controller.LocationMapController;
import com.example.animal_shelter.animal_shelter.repository.ShelterRepository;
import com.example.animal_shelter.animal_shelter.service.ShelterService;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "location_map")
public class LocationMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_path", nullable = false)
    private String filePath;
    @Column(name = "file_size", nullable = false)

    private long fileSize;
    @Column(name = "media_type", nullable = false)
    private String mediaType;
    @JsonIgnore
    private byte[] data;

    //@OneToOne
    //@Column(nullable = false)

    @Column(name = "shelter_id", nullable = false)
    private Long shelterId;

    public LocationMap(Long id, String filePath, long fileSize, String mediaType, byte[] data, Long shelterId) {
        this.id = id;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.data = data;
        this.shelterId = shelterId;
    }

    public LocationMap() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Long getShelterId() {
        return shelterId;
    }

    public void setShelterId(Long shelterId) {
        this.shelterId = shelterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationMap locationMap = (LocationMap) o;
        return fileSize == locationMap.fileSize && Objects.equals(id, locationMap.id) && Objects.equals(filePath, locationMap.filePath) && Objects.equals(mediaType, locationMap.mediaType) && Arrays.equals(data, locationMap.data) && Objects.equals(shelterId, locationMap.shelterId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, filePath, fileSize, mediaType, shelterId);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "Avatar{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", data=" + Arrays.toString(data) +
                ", shelterId=" + shelterId +
                '}';
    }
}
