package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.DocumentCat;
import com.example.animal_shelter.animal_shelter.model.TypesDocuments;
import com.example.animal_shelter.animal_shelter.service.DocumentCatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
        * <b>Контроллер DocumentCatController предназначен для вывода данных приютов
        * с использованием методов прописанных в DocumentCatService.</b>
        * Реализованы POST, PUT, DELETE запросы.
        * В дальнейшем будут добавлено множество других удобных запросов, для работы
        * с информацией о документах.
        */
@RestController
@RequestMapping("document_cat")
public class DocumentCatController {
    private final DocumentCatService documentCatService;

    public DocumentCatController(DocumentCatService documentCatService) {
        this.documentCatService = documentCatService;
    }

    @Operation(
            summary = "Вносим информацию о новом документе для кошек",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Внесенный документ для кошек",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = DocumentCat.class))
                            )
                    )
            }, tags = "Document Cat")
    @PostMapping //POST http://localhost:8080/document_cat
    public DocumentCat createDocumentCat(@Parameter(description = "Необходимо корректно" +
            " заполнить информацию о документе", example = "рекомендации"
    ) @RequestBody DocumentCat documentCat) {
        return documentCatService.createDocumentCat(documentCat);
    }
    @Operation(
            summary = "Изменяем ранее внесенную информацию о документе для кошек",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененный документ для кошек ",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = DocumentCat.class))
                            )
                    )
            }, tags = "Document Cat")
    @PutMapping //PUT http://localhost:8080/document_cat
    public DocumentCat editDocumentCat(@RequestBody DocumentCat documentCat) {
        return documentCatService.editDocumentCat(documentCat);
    }

    @Operation(
            summary = "Удаляем ранее внесенную информацию о документе для кошек",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Результат удаления документа для кошек ",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = DocumentCat.class))
                            )
                    )
            }, tags = "Document Cat")
    @DeleteMapping("{typeDocumentCat}") //DELETE http://localhost:8080/document_cat/3
    public ResponseEntity<Void> deleteDocumentCat(@PathVariable TypesDocuments typeDocumentCat) {
       documentCatService.deleteDocumentCat(typeDocumentCat);
        return ResponseEntity.ok().build();
    }
}
