package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.model.DocumentDog;
import com.example.animal_shelter.animal_shelter.model.TypesDocuments;
import com.example.animal_shelter.animal_shelter.repository.DocumentDogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <b>Сервис DocumentDogService предназначен для обработки данных о документах для собак</b>.
 * Данный класс содержит методы добавления, изменения , удаления документов в базе данных.
 * <i>Далее будут прописаны и другие удобные методы для удобства обработки и хранения данных</i>.
 */
@Service
public class DocumentDogService {

    private final Logger logger = LoggerFactory.getLogger(DocumentDogService.class);
    private final DocumentDogRepository documentDogRepository;

    public DocumentDogService(DocumentDogRepository documentDogRepository) {
        this.documentDogRepository = documentDogRepository;
    }

    /**
     * Метод createDocumentDog создает новые <b>документы для собак</b>, внося информацию о них в базу данных.
     *
     * @param documentDog параметр со значением данных <b>документа для собак</b>.
     * @return найденный <b>приют</b>.
     */
    public DocumentDog createDocumentDog(DocumentDog documentDog) {
        logger.debug("Creating a new DocumentDog:{}", documentDog);
        final var save = documentDogRepository.save(documentDog);
        logger.debug("A new DocumentDog{}", save);
        return save;
    }

    /**
     * Метод editDocumentDog изменяет уже существующую информацию в базе данных о <b>документе для собак</b>.
     *
     * @param documentDog параметр со значением данных <b>документа для собак</b>.
     * @return найденный <b>приют/b>.
     */
    public DocumentDog editDocumentDog(DocumentDog documentDog) {
        logger.debug("Edit DocumentDog:{}", documentDog);
        final var document1 = documentDogRepository.save(documentDog);
        logger.debug("DocumentDog (edit) is{}", document1);
        return document1;
    }

    /**
     * Метод deleteDocumentDog удаляет из базы данных ранее внесенную информацию о <b>документе для собак</b> в базу данных.
     *
     * @param typeDocumentDog идентификатор искомого <b>документа для собак</b>, <u>не может быть null</u>.
     */
    @Transactional
    public void deleteDocumentDog(TypesDocuments typeDocumentDog) {
        logger.debug("Delete deleteDocumentDog:{}", typeDocumentDog);
        documentDogRepository.deleteDocumentDogByTypeDocumentDog(typeDocumentDog);

    }




}
