package com.example.animal_shelter.animal_shelter.service;

import com.example.animal_shelter.animal_shelter.controller.DocumentDogController;
import com.example.animal_shelter.animal_shelter.model.DocumentCat;
import com.example.animal_shelter.animal_shelter.model.DocumentDog;
import com.example.animal_shelter.animal_shelter.model.TypesDocuments;
import com.example.animal_shelter.animal_shelter.repository.DocumentDogRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DocumentDogServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private DocumentDogRepository documentDogRepository;

    @InjectMocks
    private DocumentDogService documentDogService;

    @Test
    void createDocumentDog() throws Exception{
        final TypesDocuments typeDocument = TypesDocuments.DOCUMENTS_TO_ADOPT_ANIMAL;
        final String text = "Тестовый документ";

        DocumentDog documentDog = new DocumentDog(typeDocument,text);

        when(documentDogRepository.save(any(DocumentDog.class))).thenReturn(documentDog);

        DocumentDog created = documentDogService.createDocumentDog(documentDog);
        assertNotNull(created);
        assertEquals(typeDocument, created.getTypeDocumentDog());
        assertEquals(text, created.getText());

    }
    @Test
    void createDocumentDogNull() {
        DocumentDog documentDog = null;
        when(documentDogRepository.save(documentDog)).thenReturn(null);

        DocumentDog result = documentDogService.createDocumentDog(documentDog);

        assertNull(result);
    }
    @Test
    void editDocumentDog() throws Exception{
        final TypesDocuments typeDocumentDog = TypesDocuments.DOCUMENTS_TO_ADOPT_ANIMAL;
        final String text = "Тестовый документ";

        final TypesDocuments newTypeDocumentDog = TypesDocuments.DOCUMENTS_TO_ADOPT_ANIMAL;
        final String newText = "Тестовый документ новый";

        DocumentDog documentDog = new DocumentDog(typeDocumentDog,text);
        DocumentDog newDocumentDog = new DocumentDog(newTypeDocumentDog,newText);

        when(documentDogRepository.save(documentDog)).thenReturn(newDocumentDog);
        when(documentDogRepository.save(newDocumentDog)).thenReturn(newDocumentDog);

        DocumentDog created = documentDogService.createDocumentDog(documentDog);
        DocumentDog created2 = documentDogService.createDocumentDog(newDocumentDog);

        assertNotNull(created);
        assertNotNull(created2);
        assertEquals(created.getTypeDocumentDog(), created.getTypeDocumentDog());
        verify(documentDogRepository, times(1)).save(newDocumentDog);
    }

    @Test
    void deleteDocumentDog() throws Exception{
        final TypesDocuments typeDocumentDog = TypesDocuments.DOCUMENTS_TO_ADOPT_ANIMAL;
        final String text = "Тестовый документ";

        DocumentDog documentDog = new DocumentDog();
        documentDog.setTypeDocumentDog(typeDocumentDog);
        documentDog.setText(text);

        doNothing().when(documentDogRepository).deleteDocumentDogByTypeDocumentDog(typeDocumentDog);
        documentDogService.deleteDocumentDog(typeDocumentDog);

        verify(documentDogRepository, times(1)).deleteDocumentDogByTypeDocumentDog(typeDocumentDog);

    }
}