package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.LocationMap;
import com.example.animal_shelter.animal_shelter.model.Shelter;
import com.example.animal_shelter.animal_shelter.service.LocationMapService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
/**
 * <b>Контроллер LocationMapController предназначен для вывода схем проезда к приютам
 * с использованием методов прописанных в LocationMapService.</b>
 * Реализованы POST, PUT, DELETE запросы.
 * В дальнейшем будут добавлено множество других удобных запросов, для работы
 * со схемами проезда.
 */
@RestController
@RequestMapping("/location_maps")
public class LocationMapController {
    private final LocationMapService locationMapService;

    public LocationMapController(LocationMapService locationMapService) {
        this.locationMapService = locationMapService;
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Загружаем файл ",
                    content = @Content(
                            schema = @Schema(implementation = LocationMap.class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })

    @PostMapping( value ="{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //POST http://localhost:8080/location_maps
    public ResponseEntity<String> uploadlocationMap(@PathVariable Long id, @RequestParam MultipartFile locationMap) throws IOException {
        if(locationMap.getSize()>=1024*300){
            return ResponseEntity.badRequest().body("File is too big");
        }
        locationMapService.uploadLocationMap(id,locationMap);
        return ResponseEntity.ok().build();
    }


    @ApiResponses({
            @ApiResponse(
                    description = "Получаем все файлы ",
                    content = @Content(
                            schema = @Schema(implementation = LocationMap.class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @GetMapping()//GET http://localhost:8080/location_maps
    public ResponseEntity<List<LocationMap>> getAllLocationMaps(@RequestParam("page") Integer pageNumber,
                                                           @RequestParam("size") Integer pageSize) {
        List<LocationMap> locationMapList = locationMapService.getAllLocationMaps(pageNumber, pageSize);
        return ResponseEntity.ok(locationMapList);
    }

    @ApiResponses({
            @ApiResponse(
                    description = "Получаем все файлы ",
                    content = @Content(
                            schema = @Schema(implementation = LocationMap.class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @GetMapping("{id}") //GET http://localhost:8080/location_maps/3
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException{
        LocationMap locationMap = locationMapService.findLocationMap(id);
        Path path = Path.of(locationMap.getFilePath());
        try(InputStream is = Files.newInputStream(path);
            OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(locationMap.getMediaType());
            response.setContentLength((int) locationMap.getFileSize());
            is.transferTo(os);
        }
    }

//закомментировала, т.к. используем разные базы данных
//    @GetMapping(value = "/{id}/avatar-from-db")
//    public ResponseEntity<byte[]> downloadLocationMaps(@PathVariable Long id) {
//        LocationMap locationMap = locationMapService.findLocationMap(id);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType(locationMap.getMediaType()));
//        headers.setContentLength(locationMap.getData().length);
//        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(locationMap.getData());
//    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Удаляем ранее внесенную информацию о схеме данных ",
                    content = @Content(
                            schema = @Schema(implementation = Shelter[].class),
                            examples = @ExampleObject(externalValue = ".......допишем позже")
                    )
            )
    })
    @DeleteMapping("{id}") //DELETE http://localhost:8080/location_maps/3
    public ResponseEntity deleteLocationMap(@PathVariable Long id) {
        locationMapService.deleteLocationMap(id);
        return ResponseEntity.ok().build();
    }


}
