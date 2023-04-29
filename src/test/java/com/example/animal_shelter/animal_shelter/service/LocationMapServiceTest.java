package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.model.LocationMap;
import com.example.animal_shelter.animal_shelter.model.Shelter;
import com.example.animal_shelter.animal_shelter.model.TypesShelters;
import com.example.animal_shelter.animal_shelter.repository.LocationMapRepository;
import com.example.animal_shelter.animal_shelter.repository.ShelterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationMapServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private LocationMapRepository locationMapRepository;
    @InjectMocks
    private LocationMapService locationMapService;
    @Mock
    private ShelterRepository shelterRepository;
    @Mock
    private ShelterService shelterService;
    @Test
    void uploadLocationMap() throws Exception{
        String nameShelter = "Приют 123";
        Long idShelter = 1L;

        Shelter shelter = new Shelter();
        shelter.setName(nameShelter);
        shelter.setTypeShelter(TypesShelters.DOG_SHELTER);
        shelter.setId(1L);

        Long id = 1L;

        LocationMap locationMap = new LocationMap();
        locationMap.setId(id);
        locationMap.setShelterId(idShelter);
        locationMapService.setLocationMapDir("locationMap");
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                LocationMapServiceTest.class.getResourceAsStream("hello.jpg")
        );

        when(shelterService.findShelter(any(Long.class))).thenReturn(shelter);
        when(locationMapRepository.save(any(LocationMap.class))).thenReturn(locationMap);

       locationMapService.uploadLocationMap(id,file);
    }

    @Test
    void findLocationMap() throws Exception{
        String nameShelter = "Приют 123";
        Long idShelter = 1L;

        Shelter shelter = new Shelter();
        shelter.setName(nameShelter);
        shelter.setTypeShelter(TypesShelters.DOG_SHELTER);
        shelter.setId(1L);

        Long id = 1L;

        LocationMap locationMap = new LocationMap();
        locationMap.setId(id);
        locationMap.setShelterId(idShelter);

        LocationMap locationMap2 = new LocationMap();
        locationMap2.setId(id);
        locationMap2.setShelterId(idShelter);

        when(locationMapRepository.findById(id)).thenReturn(Optional.of(locationMap));

        LocationMap created = locationMapService.findLocationMap(id);

        assertNotNull(created);
        assertEquals(created.getId(), locationMap2.getId());
    }

    @Test
    void getAllLocationMaps() throws Exception{
        String nameShelter = "Приют 123";
        Long idShelter = 1L;

        Shelter shelter = new Shelter();
        shelter.setName(nameShelter);
        shelter.setTypeShelter(TypesShelters.DOG_SHELTER);
        shelter.setId(1L);

        Long id = 1L;

        LocationMap locationMap = new LocationMap();
        locationMap.setId(id);
        locationMap.setShelterId(idShelter);

        List<LocationMap> list = new ArrayList<>();
        list.add(locationMap);

        Page<LocationMap> page = new PageImpl<>(list);

        when(locationMapRepository.findAll(any(Pageable.class))).thenReturn(page);

        Collection<LocationMap> result = locationMapService.getAllLocationMaps(0,1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(locationMap));
    }

    @Test
    void deleteLocationMap() throws Exception{
        String nameShelter = "Приют 123";
        Long idShelter = 1L;

        Shelter shelter = new Shelter();
        shelter.setName(nameShelter);
        shelter.setTypeShelter(TypesShelters.DOG_SHELTER);
        shelter.setId(1L);

        Long id = 1L;

        LocationMap locationMap = new LocationMap();
        locationMap.setId(id);
        locationMap.setShelterId(idShelter);


        doNothing().when(locationMapRepository).deleteById(id);
        locationMapService.deleteLocationMap(id);
        verify(locationMapRepository, atLeastOnce()).deleteById(id);

    }
}