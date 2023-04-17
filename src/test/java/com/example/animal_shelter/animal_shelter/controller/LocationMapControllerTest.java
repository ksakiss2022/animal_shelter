package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.LocationMap;
import com.example.animal_shelter.animal_shelter.model.Shelter;
import com.example.animal_shelter.animal_shelter.repository.LocationMapRepository;
import com.example.animal_shelter.animal_shelter.repository.ShelterRepository;
import com.example.animal_shelter.animal_shelter.service.LocationMapService;
import com.example.animal_shelter.animal_shelter.service.ShelterService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.mockito.ArgumentMatchers.any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;
import java.nio.file.FileSystem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LocationMapController.class)
class LocationMapControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LocationMapRepository locationMapRepository;
    @MockBean
    private ShelterRepository shelterRepository;

    @SpyBean
    private LocationMapService locationMapService;

    @SpyBean
    private ShelterService shelterService;

    @InjectMocks
    private LocationMapController locationMapController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    void uploadLocationMap() throws Exception{

        String nameShelter = "Приют 123";
        String informationShelter = "Тестовый приют для собак";
        String scheduleShelter = "Расписание тестового приюта - с 10 до 18";
        String addressShelter = "Адрес тестового приюта - д.Простоквашино";
        String safetyRecommendationsShelter = "ТБ - осторожность и аккуратность";
        Long idShelter = 1L;

        Shelter shelter = new Shelter();
        shelter.setName(nameShelter);
        shelter.setInformation(informationShelter);
        shelter.setSchedule(scheduleShelter);
        shelter.setAddress(addressShelter);
        shelter.setSafetyRecommendations(safetyRecommendationsShelter);

        when(shelterRepository.findById(idShelter)).thenReturn(Optional.of(shelter));

        Long id = 1L;
        String filePath = "hello.jpg";
        Long fileSize = 38213L;
        String mediaType = "image/jpeg";
        byte[] data = "Hello".getBytes();
        Long shelterId = 1L;

        LocationMap locationMap = new LocationMap();
        locationMap.setId(id);
        locationMap.setFilePath(filePath);
        locationMap.setFileSize(fileSize);
        locationMap.setMediaType(mediaType);
        locationMap.setData(data);
        locationMap.setShelterId(shelterId);

        when(locationMapRepository.findLocationMapByShelterId(shelterId)).thenReturn(Optional.of(locationMap));
        when(locationMapRepository.save(any(LocationMap.class))).thenReturn(locationMap);
        when(locationMapRepository.findLocationMapById(id)).thenReturn(Optional.of(locationMap));

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.jpeg",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "Hello".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders
                                .multipart("/location_maps/{id}",shelterId)
                                .file("locationMap", file.getBytes())
                                .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk()) ;
    }

    @Test
    void getAllLocationMaps() throws Exception{

        Long id = 1L;
        String filePath = "hello.jpg";
        Long fileSize = 38213L;
        String mediaType = "image/jpeg";
        byte[] data = "Hello".getBytes();
        Long shelterId = 1L;

        LocationMap locationMap = new LocationMap();
        locationMap.setId(id);
        locationMap.setFilePath(filePath);
        locationMap.setFileSize(fileSize);
        locationMap.setMediaType(mediaType);
        locationMap.setData(data);
        locationMap.setShelterId(shelterId);
        List<LocationMap> list = new ArrayList<>();
        list.add(locationMap);

        Page<LocationMap> page = new PageImpl<>(list);

        when(locationMapRepository.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/location_maps/", 0, 1)
                        .content(page.getContent().toString())
                )
                .andExpect(result -> list.toString()) ;
    }

    @Test
    void downloadLocationMap() throws Exception {
        Long id = 1L;
        String filePath = "locationMaps\\Shelter{id=1, name='приют1', information='инфа', schedule='расписание', address='адрес', safetyRecommendations='тб'}.jpg";//"hello.jpg";

        Long fileSize = 38213L;
        String mediaType = "image/jpeg";
        byte[] data = "Hello".getBytes();
        Long shelterId = 1L;

        LocationMap locationMap = new LocationMap();
        locationMap.setId(id);
        locationMap.setFilePath(filePath.toString());
        locationMap.setFileSize(fileSize);
        locationMap.setMediaType(mediaType);
        locationMap.setData(data);
        locationMap.setShelterId(shelterId);

        when(locationMapRepository.findLocationMapById(any(Long.class))).thenReturn(Optional.of(locationMap));
        InputStream is = mock(InputStream.class);
        FileSystem fileSystem = mock(FileSystem.class);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/location_maps/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void deleteLocationMap() throws Exception{
        Long id = 1L;
        String filePath = "hello.jpg";

        Long fileSize = 38213L;
        String mediaType = "image/jpeg";
        byte[] data = "Hello".getBytes();
        Long shelterId = 1L;

        LocationMap locationMap = new LocationMap();
        locationMap.setId(id);
        locationMap.setFilePath(filePath.toString());
        locationMap.setFileSize(fileSize);
        locationMap.setMediaType(mediaType);
        locationMap.setData(data);
        locationMap.setShelterId(shelterId);


        when(locationMapRepository.findById(id)).thenReturn(Optional.of(locationMap));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/location_maps/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(locationMapRepository, atLeastOnce()).deleteById(id);

    }
}