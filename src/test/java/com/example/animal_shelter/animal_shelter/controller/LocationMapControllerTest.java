package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.LocationMap;
import com.example.animal_shelter.animal_shelter.model.Shelter;
import com.example.animal_shelter.animal_shelter.model.TypesShelters;
import com.example.animal_shelter.animal_shelter.repository.LocationMapRepository;
import com.example.animal_shelter.animal_shelter.repository.ShelterRepository;
import com.example.animal_shelter.animal_shelter.service.LocationMapService;
import com.example.animal_shelter.animal_shelter.service.ShelterService;
import org.apache.catalina.connector.ResponseFacade;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LocationMapController.class)
class LocationMapControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LocationMapRepository locationMapRepository;
    @MockBean
    private ShelterRepository shelterRepository;

    @MockBean
    private LocationMapService locationMapService;

    @SpyBean
    private ShelterService shelterService;

    @InjectMocks
    private LocationMapController locationMapController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    void uploadLocationMap() throws Exception {

        String nameShelter = "Приют 123";
        String informationShelter = "Тестовый приют для собак";
        String scheduleShelter = "Расписание тестового приюта - с 10 до 18";
        String addressShelter = "Адрес тестового приюта - д.Простоквашино";
        String safetyRecommendationsShelter = "ТБ - осторожность и аккуратность";
        Long idShelter = 1L;

        Shelter shelter = new Shelter();
        shelter.setName(nameShelter);
        shelter.setTypeShelter(TypesShelters.DOG_SHELTER);
        shelter.setId(1L);

        Long id = 1L;
        Long shelterId = 1L;

        LocationMap locationMap = new LocationMap();
        locationMap.setId(id);
        locationMap.setShelterId(idShelter);

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                LocationMapControllerTest.class.getResourceAsStream("hello.jpg")
        );

        when(shelterRepository.findById(idShelter)).thenReturn(Optional.of(shelter));
        when(locationMapRepository.save(any(LocationMap.class))).thenReturn(locationMap, locationMap);

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/location_maps/{id}", shelterId)
                        .file(file)
                )
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
                });
    }

    @Test
    void getAllLocationMaps() throws Exception{
        String nameShelter = "Приют 123";
        String informationShelter = "Тестовый приют для собак";
        String scheduleShelter = "Расписание тестового приюта - с 10 до 18";
        String addressShelter = "Адрес тестового приюта - д.Простоквашино";
        String safetyRecommendationsShelter = "ТБ - осторожность и аккуратность";
        Long idShelter = 1L;

        Shelter shelter = new Shelter();
        shelter.setName(nameShelter);
        shelter.setTypeShelter(TypesShelters.DOG_SHELTER);
        shelter.setId(1L);

        Long id = 1L;
        Long shelterId = 1L;

        LocationMap locationMap = new LocationMap();
        locationMap.setId(id);
        locationMap.setShelterId(idShelter);

        List<LocationMap> list = new ArrayList<>();
        list.add(locationMap);

        Page<LocationMap> page = new PageImpl<>(list);

        when(locationMapRepository.findAll(any(Pageable.class))).thenReturn(page);
        //when(Files.newInputStream(any())).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/location_maps/" )
                        .param("page","0")
                        .param("size","1")
                        .content(page.getContent().toString()
                        )
                )
                .andExpect(result -> list.toString()) ;
    }

    @Test
    void downloadLocationMap() throws Exception {
//        String nameShelter = "Приют 123";
//        Long idShelter = 1L;
//
//        Shelter shelter = new Shelter();
//        shelter.setName(nameShelter);
//        shelter.setTypeShelter(TypesShelters.DOG_SHELTER);
//        shelter.setId(1L);
//        Long id = 1L;
//
//        MockMultipartFile file
//                = new MockMultipartFile(
//                "file",
//                "hello.jpg",
//                MediaType.IMAGE_JPEG_VALUE,
//                LocationMapControllerTest.class.getResourceAsStream("hello.jpg"));
//
//        LocationMap locationMap = new LocationMap();
//        locationMap.setId(id);
//        locationMap.setShelterId(idShelter);
//        String filePath = ""; //"locationMaps\\null.jpg";
//        locationMap.setFilePath(filePath);
//
//        Path path = Path.of(locationMap.getFilePath());
//
//        List<LocationMap> allLocationMaps = new ArrayList<>();
//        allLocationMaps.add(locationMap);
//        PageRequest pageRequest = PageRequest.of(0,1);
//        Page<LocationMap> page = new PageImpl<>(allLocationMaps);
//
//        when(locationMapRepository.findAll(any(Pageable.class))).thenReturn((Page<LocationMap>) page);
//        when(locationMapService.findLocationMap(id)).thenReturn(locationMap);
//        InputStream is = mock(InputStream.class);
//
//        ResponseFacade t = mock(ResponseFacade.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/location_maps/{id}", id)
//                        //.params("response",  response.)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//        );
    }

    @Test
    void deleteLocationMap() throws Exception{
        Long id = 123L;
        Long shelterId = 123L;

        LocationMap locationMap = new LocationMap();
        locationMap.setId(id);
        locationMap.setShelterId(shelterId);

        when(locationMapRepository.findById(any())).thenReturn(Optional.of(locationMap));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/location_maps/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

       // verify(locationMapRepository).deleteById(id);
    }
}