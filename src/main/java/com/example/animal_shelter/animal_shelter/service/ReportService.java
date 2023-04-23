package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.model.Report;
import com.example.animal_shelter.animal_shelter.repository.ReportRepository;
import com.pengrad.telegrambot.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <b>Сервис ReportService предназначен для обработки данных об отчётах</b>.
 * Данный класс содержит методы добавления, изменения , удаления отчётов в базе данных.
 * <i>Далее будут прописаны и другие удобные методы для удобства обработки и хранения данных</i>.
 */

@Service
@Transactional
public class ReportService {
    private final Logger logger = LoggerFactory.getLogger(ReportService.class);
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /**
     * Метод createReport создает новые <b>отчёты</b>, внося онформацию о них в базу данных.
     *
     * @param report параметр со значением данных <b>владельцев собак</b>.
     * @return найденный <b>отчёт</b>.
     */
    public Report createReport(Report report) {
        logger.debug("Creating a new report:{}", report);
        final var save = reportRepository.save(report);
        logger.debug("A new report{}", save);
        return save;
    }

    /**
     * Метод editReport изменяет уже существующую информацию в базе данных об <b>отчётах</b>.
     *
     * @param report параметр со значением данных <b>отчётов</b>.
     * @return найденный <b>отчёт</b>.
     */
    public Report editReport(Report report) {
        logger.debug("Edit report:{}", report);
        if (reportRepository.findById(report.getId()).isPresent()) {
            final var report1 = reportRepository.save(report);
            logger.debug("Report (edit) is{}", report1);
            return report1;
        } else {
            logger.debug("No report found with id {}", report.getId());
            return null;
        }
    }

    /**
     * Метод deleteReport удаляет из базы данных ранее внесенную информацию о <b>отчёте</b>.
     *
     * @param id идентификатор искомого <b>отчёта</b>, <u>не может быть null</u>.
     */
    public void deleteReport(long id) {
        logger.debug("Delete report:{}", id);
        reportRepository.deleteById(id);
    }

    /**
     * Метод getAllReports выводит список обо всех <b> отчётах</b> внесенных в базу данных.
     *
     * @return найденные <b>отчёты</b>.
     */
    public Collection<Report> getAll() {
        logger.debug("Collection all reports:{}");
        final var all = reportRepository.findAll();
        logger.debug("All reports is{}", all);
        return all;
    }

    /**
     * Метод uploadReport загружает  <b>отчёт </b>.
     *
     * @param personId параметр со значением данных <b>отчёта</b>.
     */
    public void uploadReport(Long personId, byte[] pictureFile, File file, String ration, String health,
                             String habits, String filePath, Date dateSendMessage, Long timeDate, long daysOfReports) throws IOException {
        logger.info("Создание отчёта");
        Report report = new Report();
        report.setLastMessage(dateSendMessage);
        report.setDays(daysOfReports);
        report.setFilePath(filePath);
        report.setFileSize(file.fileSize());
        report.setLastMessageMs(timeDate);
        report.setChatId(personId);
        report.setData(pictureFile);
        report.setRation(ration);
        report.setHealth(health);
        report.setHabits(habits);
        report.setCaption("отсутствует");
        report.setChatId(personId);
        reportRepository.save(report);
    }

    public void uploadReport(Long personId, byte[] pictureFile, File file,
                             String caption, String filePath, Date dateSendMessage, Long timeDate, long daysOfReports) throws IOException {
        logger.info("Вызван метод для загрузки отчёта");
        Report report = new Report();
        report.setLastMessage(dateSendMessage);
        report.setDays(daysOfReports);
        report.setFilePath(filePath);
        report.setChatId(personId);
        report.setFileSize(file.fileSize());
        report.setData(pictureFile);
        report.setCaption(caption);
        report.setChatId(personId);
        report.setLastMessageMs(timeDate);
        report.setRation("не изменился");
        report.setHealth("не изменилось");
        report.setHabits("не изменились");
        reportRepository.save(report);
    }

    /**
     * Метод findReportById возвращает существующий  <b>отчёт</b>.
     *
     * @param id идентификатор искомого <b>отчёта</b>, <u>не может быть null</u>.
     */

    public Report findReportById(Long id) {
        logger.info("Requesting find report by id");
        return reportRepository.findReportById(id).orElse(null);
    }

    /**
     * Метод getAllReports возвращает из базы данных ранее внесенную информацию об <b>отчётах</b> .
     *
     * @param pageNumber номер получаемой страницы, <u>не может быть null</u>.
     * @param pageSize   размер получаемой страницы, <u>не может быть null</u>.
     */

    public List<Report> getAllReports(Integer pageNumber, Integer pageSize) {
        logger.info("Was invoked method to get all reports");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return reportRepository.findAll(pageRequest).getContent();
    }

    /**
     * Метод getExtensions -внутренний метод.
     *
     * @param fileName параметр со значением имени файла .
     * @return возвращает расширение
     */

    private String getExtensions(String fileName) {
        logger.info("Was invoked method to getExtensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


}
