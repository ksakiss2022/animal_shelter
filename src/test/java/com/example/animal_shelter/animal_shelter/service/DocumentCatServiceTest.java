package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.model.DocumentCat;
import com.example.animal_shelter.animal_shelter.model.TypesDocuments;
import com.example.animal_shelter.animal_shelter.repository.DocumentCatRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentCatServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private DocumentCatRepository documentCatRepository;
    @InjectMocks
    private DocumentCatService documentCatService;

    @Test
    void createDocumentCat() throws Exception {
        TypesDocuments typeDocument = TypesDocuments.DOCUMENTS_TO_ADOPT_ANIMAL;
        String text = "Тестовый документ";

        DocumentCat documentCat = new DocumentCat(typeDocument, text);

        when(documentCatRepository.save(any(DocumentCat.class))).thenReturn(documentCat);

        DocumentCat created = documentCatService.createDocumentCat(documentCat);
        assertNotNull(created);
        assertEquals(typeDocument, created.getTypeDocumentCat());
        assertEquals(text, created.getText());
    }
    @Test
    void createDocumentCatNull() {
        DocumentCat documentCat = null;
        when(documentCatRepository.save(documentCat)).thenReturn(null);

        DocumentCat result = documentCatService.createDocumentCat(documentCat);

        assertNull(result);
    }

    @Test
    void editDocumentCat() throws Exception{
        final TypesDocuments typeDocument = TypesDocuments.DOCUMENTS_TO_ADOPT_ANIMAL;
        final String text = "Тестовый документ";

        final TypesDocuments newTypeDocument= TypesDocuments.DOCUMENTS_TO_ADOPT_ANIMAL;
        final String newText = "Тестовый документ новый";

        DocumentCat documentCat= new DocumentCat(typeDocument,text);
        DocumentCat newDocumentCat = new DocumentCat(newTypeDocument,newText);

        when(documentCatRepository.save(documentCat)).thenReturn(documentCat);
        when(documentCatRepository.save(newDocumentCat)).thenReturn(newDocumentCat);

        DocumentCat created = documentCatService.createDocumentCat(documentCat);
        DocumentCat created2 = documentCatService.createDocumentCat(newDocumentCat);

        assertNotNull(created);
        assertNotNull(created2);
        assertEquals(created.getTypeDocumentCat(), created.getTypeDocumentCat());
        verify(documentCatRepository, times(1)).save(newDocumentCat);
    }

    @Test
    void deleteDocumentCat() throws Exception{
        final TypesDocuments typeDocument = TypesDocuments.DOCUMENTS_TO_ADOPT_ANIMAL;
        final String text = "Тестовый документ";

        DocumentCat documentCat = new DocumentCat();
        documentCat.setTypeDocumentCat(typeDocument);
        documentCat.setText(text);

        doNothing().when(documentCatRepository).deleteDocumentCatByTypeDocumentCat(typeDocument);
        documentCatService.deleteDocumentCat(typeDocument);

        verify(documentCatRepository, times(1)).deleteDocumentCatByTypeDocumentCat(typeDocument);
    }
}