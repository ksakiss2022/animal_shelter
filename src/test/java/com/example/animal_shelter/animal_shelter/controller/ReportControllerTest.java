package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.Report;
import com.example.animal_shelter.animal_shelter.repository.ReportRepository;
import com.example.animal_shelter.animal_shelter.service.ReportService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReportController.class)
public class ReportControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReportRepository reportRepository;

    @SpyBean
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    @Test
    public void createReport() throws Exception {

        final String ration = "ration good";
        final String health = "health very good";
        final String habits = "habits change";


        JSONObject reportObject = new JSONObject();
        reportObject.put("ration", ration);
        reportObject.put("health", health);
        reportObject.put("habits", habits);


        Report report = new Report(ration, health, habits);

        when(reportRepository.save(any(Report.class))).thenReturn(report);
        when(reportRepository.findById(any(Long.class))).thenReturn(Optional.of(report));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reports")
                        .content(reportObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ration").value(ration))
                .andExpect(jsonPath("$.health").value(health))
                .andExpect(jsonPath("$.habits").value(habits));
    }


    @Test
    public void editReport() throws Exception {

        Long id = 1L;
        String oldRation = "50 грамм";
        String oldHealth = "Хорошее";
        String oldHabits = "Хорошо охотится";

        String newRation = "60 грамм";
        String newHealth = "Отличное";
        String newHabits = "Охотится ещё лучше";

        JSONObject reportObject = new JSONObject();

        reportObject.put("ration", newRation);
        reportObject.put("health", newHealth);
        reportObject.put("habits", newHabits);

        Report report = new Report(oldRation, oldHealth, oldHabits);

        Report editReport = new Report(newRation, newHealth, newHabits);

        when(reportRepository.findById(id)).thenReturn(Optional.of(report));
        when(reportRepository.save(any(Report.class))).thenReturn(editReport);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/reports")
                        .content(reportObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ration").value(newRation))
                .andExpect(jsonPath("$.health").value(newHealth))
                .andExpect(jsonPath("$.habits").value(newHabits));
    }

    @Test
    void deleteReport() throws Exception {
        mockMvc.perform(
                        delete("/reports/{id}", 1))
                .andExpect(status().isOk());
        verify(reportService).deleteReport(1L);
    }


    @Test
    void findReport() throws Exception {
        when(reportService.getAll()).thenReturn(List.of(new Report()));

        mockMvc.perform(
                        get("/reports"))
                .andExpect(status().isOk());
    }
}