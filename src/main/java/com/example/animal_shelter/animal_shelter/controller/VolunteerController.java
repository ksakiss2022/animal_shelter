package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.Cat;
import com.example.animal_shelter.animal_shelter.model.Volunteer;
import com.example.animal_shelter.animal_shelter.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("volunteers")
public class VolunteerController {
    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Operation(
            summary = "Вносим информацию о новой кошке",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Внесенная информация о кошке ",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                            )
                    )
            },
            tags = "Volunteers"
    )
    @PostMapping //POST http://localhost:8080/volunteers
    public Volunteer createvolunteers(@Parameter(description = "Необходимо корректно" +
            " заполнить информацию о кошке(коте)", example = "Васька"
    ) @RequestBody Volunteer volunteer) {
        return volunteerService.createVolunteer(volunteer);
    }


    @Operation(
            summary = "Изменяем ранее внесенную информацию о кошке",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Изменяемая кошка ",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                            )
                    )
            },
            tags = "Volunteers"
    )
    @PutMapping //PUT http://localhost:8080/volunteers
    public Volunteer editVolunteer(@RequestBody Volunteer volunteer1) {
        return volunteerService.editVolunteer(volunteer1);
    }


    @Operation(
            summary = "Удаляем ранее внесенную информацию о кошке",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаляем кошку ",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                            )
                    )
            },
            tags = "Volunteers"
    )
    @DeleteMapping("{id}") //DELETE http://localhost:8080/volunteers/3
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Long id) {
        volunteerService.deleteVolunteer(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Поиск кошек по кличке, породе , и вывод списка всех кошек",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденные кошки.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                            )
                    )
            },
            tags = "Volunteers"
    )

    @GetMapping //GET http://localhost:8080/volunteers
    public ResponseEntity<?> findVolunteers(
            @Parameter(description = "Кличка кошки, часть клички, прописными или заглавными буквами", example = "пример заполнение:ВАська")
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "spatialization") String spatialization) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(volunteerService.findVolunteerByName(name));
        }
        if (spatialization != null && !spatialization.isBlank()) {
            return ResponseEntity.ok(volunteerService.findVolunteerBySpatialization(spatialization));
        }
        return ResponseEntity.ok(volunteerService.getAllVolunteers());
    }
}
