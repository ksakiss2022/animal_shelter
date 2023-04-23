package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.model.DocumentCat;
import com.example.animal_shelter.animal_shelter.model.TypesDocuments;
import com.example.animal_shelter.animal_shelter.repository.DocumentCatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <b>Сервис DocumentCatService предназначен для обработки данных о документах для кошек</b>.
 * Данный класс содержит методы добавления, изменения , удаления документов в базе данных.
 * <i>Далее будут прописаны и другие удобные методы для удобства обработки и хранения данных</i>.
 */
@Service
public class DocumentCatService {
    private final Logger logger = LoggerFactory.getLogger(DocumentDogService.class);
    private final DocumentCatRepository documentCatRepository;

    public DocumentCatService(DocumentCatRepository documentCatRepository) {
        this.documentCatRepository = documentCatRepository;
    }

    /**
     * Метод createDocumentCat создает новые <b>документы для кошек</b>, внося информацию о них в базу данных.
     *
     * @param documentCat параметр со значением данных <b>документа для кошек</b>.
     * @return найденный <b>приют</b>.
     */
    public DocumentCat createDocumentCat(DocumentCat documentCat) {
        logger.debug("Creating a new DocumentDog:{}", documentCat);
        final var save = documentCatRepository.save(documentCat);
        logger.debug("A new DocumentCat{}", save);
        return save;
    }

    /**
     * Метод editDocumentCat изменяет уже существующую информацию в базе данных о <b>документе для кошек</b>.
     *
     * @param documentCat параметр со значением данных <b>документа для кошек</b>.
     * @return найденный <b>приют/b>.
     */
    public DocumentCat editDocumentCat(DocumentCat documentCat) {
        logger.debug("Edit DocumentCat:{}", documentCat);

        if(documentCatRepository.findDocumentCatByTypeDocumentCat(documentCat.getTypeDocumentCat()).equals(documentCat)) {
            final var document1 = documentCatRepository.save(documentCat);
            logger.debug("documentCat (edit) is{}", document1);
            return document1;
        } else {
            logger.debug("No document cat found with id {}", documentCat.getTypeDocumentCat());
            return null;
        }

    }

    /**
     * Метод deleteDocumentCat удаляет из базы данных ранее внесенную информацию о <b>документе для кошек</b> в базу данных.
     *
     * @param typeDocument идентификатор искомого <b>документа для кошек</b>, <u>не может быть null</u>.
     */
    @Transactional
    public void deleteDocumentCat(TypesDocuments typeDocument) {
        logger.debug("Delete deleteDocumentCat:{}", typeDocument);
        documentCatRepository.deleteDocumentCatByTypeDocumentCat(typeDocument);

    }
}
