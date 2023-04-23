package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.model.Report;
import com.example.animal_shelter.animal_shelter.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    private Logger logger;
    @InjectMocks
    private ReportService reportService;
    @Mock
    private ReportRepository reportRepository;
    private Report expectedReport;

    @BeforeEach
    public void setup() {
        expectedReport = new Report();
        expectedReport.setId(1L);
        expectedReport.setRation("Хороший");
        expectedReport.setHealth("Отличное");
        expectedReport.setHabits("Не изменились");
    }

    @Test
    public void shouldCreateReport() {
        when(reportRepository.save(any())).thenReturn(expectedReport);
        Report actualReport = reportService.createReport(expectedReport);
        assertEquals(expectedReport, actualReport);
    }


    @Test
    public void shouldEditReport() {
        Long reportId = 1L;
        String newRation = "Хороший";

        Report existingReport = new Report();
        existingReport.setId(reportId);
        existingReport.setRation("Плохой");

        Report updatedReport = new Report();
        updatedReport.setId(reportId);
        updatedReport.setRation(newRation);

        // mockito - when -> then
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(existingReport));
        when(reportRepository.save(updatedReport)).thenReturn(updatedReport);

        Report result = reportService.editReport(updatedReport);

        assertNotNull(result);
        assertEquals(newRation, result.getRation());
        assertEquals(existingReport.getHealth(), result.getHealth());
        assertEquals(existingReport.getHabits(), result.getHabits());

        // verify if the repository save method was called
        verify(reportRepository, times(1)).save(updatedReport);
    }

    @Test
    public void shouldReturnNullWhenNoReportFound() {
        Long reportId = 1L;

        Report updatedReport = new Report();
        updatedReport.setId(reportId);

        // mockito - when -> then
        when(reportRepository.findById(reportId)).thenReturn(Optional.empty());

        Report result = reportService.editReport(updatedReport);

        assertNull(result);

        // verify if the repository save method was never called
        verify(reportRepository, never()).save(updatedReport);
        //В данном тесте происходит создание объектов Report и проверка изменения рациона. Методы mockito when ->
        // then используются для простой имитации репозитория и его методов findById() и save().
        // Также используется метод verify(), который подсчитывает количество вызовов метода save() в репозитории.

    }

    @Test
    public void shouldDeleteReport() {
        Long reportId = 1L;

        // mockito - doNothing() example
        doNothing().when(reportRepository).deleteById(reportId);

        reportService.deleteReport(reportId);

        verify(reportRepository, times(1)).deleteById(reportId);
        //В данном тесте создается идентификатор собаки,
        // затем подготавливается мок объект reportRepository для метода deleteById()
        // с этим идентификатором. Вызывается deleteReport() метод из reportService, затем проверяется,
        // что deleteById() метод был вызван из reportRepository объекта.
    }

    @Test
    public void shouldReturnAllReports() {
        Report report1 = new Report();
        report1.setId(1L);
        report1.setRation("Мелани");
        report1.setHealth("Бигль");
        report1.setHabits("Зайка");

        Report report2 = new Report();
        report2.setId(2L);
        report2.setRation("Рекс");
        report2.setHealth("Овчарка");
        report2.setHabits("Комиссар.");

        List<Report> allReports = new ArrayList<>();
        allReports.add(report1);
        allReports.add(report2);

        when(reportRepository.findAll()).thenReturn(allReports);

        Collection<Report> result = reportService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(report1));
        assertTrue(result.contains(report2));
    }

    @Test
    public void shouldReturnEmptyListWhenNoReports() {
        List<Report> allReports = new ArrayList<>();

        when(reportRepository.findAll()).thenReturn(allReports);

        Collection<Report> result = reportService.getAll();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

}
