package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.model.LocationMap;
import com.example.animal_shelter.animal_shelter.model.Shelter;
import com.example.animal_shelter.animal_shelter.repository.LocationMapRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Service
@Transactional
public class LocationMapService {
    @Value("locationMaps")
    private String locationMapDir;

        private ShelterService shelterService;
        private final LocationMapRepository locationMapRepository;

        private final Logger logger = LoggerFactory.getLogger(LocationMapService.class);

        public LocationMapService(ShelterService shelterService, LocationMapRepository locationMapRepository) {
            this.shelterService = shelterService;
            this.locationMapRepository = locationMapRepository;
        }
    /**
     * Метод uploadLocationMap загружает файл  <b>схемы проезда </b>.
     *
     * @param shelterId параметр со значением данных <b>приюта</b>.
     *  @param locationMapFile параметр со значением данных <b>файла</b>.
     */
        public void uploadLocationMap(Long shelterId, MultipartFile locationMapFile) throws IOException {
            logger.debug("Uploading location map");
            Shelter shelter = shelterService.findShelter(shelterId);
            Path filePath = Path.of(locationMapDir, shelter + "." + getExtensions(locationMapFile.getOriginalFilename()));
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
            try (
                    InputStream is = locationMapFile.getInputStream();
                    OutputStream os = Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW);
                    BufferedInputStream bis = new BufferedInputStream(is, 1024);
                    BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
            ) {
                bis.transferTo(bos);
            }
            LocationMap locationMap = findLocationMap(shelterId);
            locationMap.setShelterId(shelterId);
            locationMap.setFilePath(filePath.toString());
            locationMap.setFileSize(locationMapFile.getSize());
            locationMap.setMediaType(locationMapFile.getContentType());
            locationMap.setData(locationMapFile.getBytes());
           locationMapRepository.save(locationMap);
            logger.debug("Finish uploading location map");
        }

    /**
     * Метод getExtensions -внутренний метод.
     *
     * @param fileName параметр со значением имени файла .
     *  @return возвращает расширение
     */
        public  String getExtensions(String fileName) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
    /**
     * Метод findLocationMap возвращает существующую  <b>схему проезда</b>.
     *
     * @param id идентификатор искомой <b>схемы проезда</b>, <u>не может быть null</u>.
     */
        public LocationMap findLocationMap(long id) {
            logger.debug("Requesting fnd location map by id");
            return locationMapRepository.findById(id).orElse(new LocationMap());
        }

    /**
     * Метод getAllLocationMaps возвращает из базы данных ранее внесенную информацию о <b>схемах проезда</b> .
     *
     * @param pageNumber номер получаемой страницы, <u>не может быть null</u>.
     * @param pageSize размер получаемой страницы, <u>не может быть null</u>.
     */
        public List<LocationMap> getAllLocationMaps(Integer pageNumber, Integer pageSize) {
            logger.debug("getting all location maps");
            PageRequest pageRequest = PageRequest.of(pageNumber,pageSize);
            return locationMapRepository.findAll(pageRequest).getContent();
        }

    /**
     * Метод deleteLocationMap удаляет из базы данных ранее внесенную информацию о <b>схеме данных</b> в базу данных.
     *
     * @param id идентификатор искомой <b>схемы проезда</b>, <u>не может быть null</u>.
     */
    public void deleteLocationMap(long id) {
        logger.debug("Delete LocationMap:{}", id);
        locationMapRepository.deleteById(id);
    }

    public void setLocationMapDir(String locationMapDir) {
        this.locationMapDir = locationMapDir;
    }
}
