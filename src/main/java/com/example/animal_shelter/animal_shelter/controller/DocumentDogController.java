package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.DocumentDog;
import com.example.animal_shelter.animal_shelter.model.Shelter;
import com.example.animal_shelter.animal_shelter.model.TypesDocumentDog;
import com.example.animal_shelter.animal_shelter.service.DocumentDogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <b>Контроллер DocumentDogController предназначен для вывода данных приютов
 * с использованием методов прописанных в DocumentDogService.</b>
 * Реализованы POST, PUT, DELETE запросы.
 * В дальнейшем будут добавлено множество других удобных запросов, для работы
 * с информацией о документах.
 */
@RestController
@RequestMapping("document_dog")
public class DocumentDogController {
    private final DocumentDogService documentDogService;

    public DocumentDogController(DocumentDogService documentDogService) {
        this.documentDogService = documentDogService;
    }

    @Operation(
            summary = "Вносим информацию о новом документе для собак",
            responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Внесенный документ для собак",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = DocumentDog.class))
                    )
            )
    }, tags = "Document Dog")
    @PostMapping //POST http://localhost:8080/document_dog
    public DocumentDog createDocumentDog(@Parameter(description = "Необходимо корректно" +
            " заполнить информацию о документе", example = "рекомендации"
    ) @RequestBody DocumentDog documentDog) {
        return documentDogService.createDocumentDog(documentDog);
    }
    @Operation(
            summary = "Изменяем ранее внесенную информацию о документе для собак",
            responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Измененный документ для собак ",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = DocumentDog.class))
                    )
            )
    }, tags = "Document Dog")
    @PutMapping //PUT http://localhost:8080/document_dog
    public DocumentDog editShelter(@RequestBody DocumentDog documentDog) {
        return documentDogService.editDocumentDog(documentDog);
    }

    @Operation(
            summary = "Удаляем ранее внесенную информацию о документе для собак",
            responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Результат удаления документа для собак ",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = DocumentDog.class))
                    )
            )
    }, tags = "Document Dog")
    @DeleteMapping("{typeDocumentDog}") //DELETE http://localhost:8080/document_dog/3
    public ResponseEntity deleteDocumentDog(@PathVariable TypesDocumentDog typeDocumentDog) {
        documentDogService.deleteDocumentDog(typeDocumentDog);
        return ResponseEntity.ok().build();
    }
}
