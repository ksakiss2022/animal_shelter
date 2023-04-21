package com.example.animal_shelter.animal_shelter.controller;

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
@RequestMapping("volunteer")
public class VolunteerController {
    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Operation(
            summary = "Вносим информацию о новом волонтере",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Внесенная информация о волонтере ",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                            )
                    )
            },
            tags = "Volunteers"
    )
    @PostMapping //POST http://localhost:8080/volunteers
    public Volunteer createVolunteer(@Parameter(description = "Необходимо корректно" +
            " заполнить информацию о волонтере", example = "Борис"
    ) @RequestBody Volunteer volunteer) {
        return volunteerService.createVolunteer(volunteer);
    }

    @Operation(
            summary = "Поиск волонтера по имени, вывод списка всех волонтеров",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденные волонтеры.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                            )
                    )
            },
            tags = "Volunteers"
    )
    @GetMapping //GET http://localhost:8080/volunteers
    public ResponseEntity<?> getVolunteers(
            @Parameter(description = "Имя волонтера, часть имени, прописными или заглавными буквами", example = "пример заполнение:ИгОрь")
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "phone") String phone) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(volunteerService.findVolunteerByName(name));
        }
        if (phone != null && !phone.isBlank()) {
            return ResponseEntity.ok(volunteerService.findVolunteerByPhone(phone));
        }
        return ResponseEntity.ok(volunteerService.getVolunteers());
    }
}
