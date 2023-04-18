package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.DocumentDog;
import com.example.animal_shelter.animal_shelter.model.TypesDocuments;
import com.example.animal_shelter.animal_shelter.repository.DocumentDogRepository;
import com.example.animal_shelter.animal_shelter.service.DocumentDogService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DocumentDogController.class)
class DocumentDogControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DocumentDogRepository documentDogRepository;

    @SpyBean
    private DocumentDogService documentDogService;

    @InjectMocks
    private DocumentDogController documentDogController;



    @Test
    void createDocumentDog() throws Exception{
        final TypesDocuments typeDocumentDog = TypesDocuments.DOCUMENTS_TO_ADOPT_ANIMAL;
        final String text = "Тестовый документ";

        JSONObject documentDogObject = new JSONObject();
        documentDogObject.put("typeDocumentDog",typeDocumentDog);
        documentDogObject.put("text",text);

        DocumentDog documentDog = new DocumentDog(typeDocumentDog,text);

        when(documentDogRepository.save(any(DocumentDog.class))).thenReturn(documentDog);
        when(documentDogRepository.findDocumentDogByTypeDocumentDog(any(TypesDocuments.class))).thenReturn(documentDog);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/document_dog")
                        .content(documentDogObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.typeDocumentDog").value(String.valueOf(typeDocumentDog)))
                .andExpect(jsonPath("$.text").value(text));
    }

    @Test
    void editShelter() throws Exception{
        final TypesDocuments typeDocumentDog = TypesDocuments.DOCUMENTS_TO_ADOPT_ANIMAL;
        final String text = "Тестовый документ";

        final TypesDocuments newTypeDocumentDog = TypesDocuments.DOCUMENTS_TO_ADOPT_ANIMAL;
        final String newText = "Тестовый документ новый";

        JSONObject documentDogObject = new JSONObject();
        documentDogObject.put("typeDocumentDog",typeDocumentDog);
        documentDogObject.put("text",text);

        DocumentDog documentDog = new DocumentDog(typeDocumentDog,text);
        DocumentDog newDocumentDog = new DocumentDog(newTypeDocumentDog,newText);

        when(documentDogRepository.save(any(DocumentDog.class))).thenReturn(newDocumentDog);
        when(documentDogRepository.findDocumentDogByTypeDocumentDog(any(TypesDocuments.class))).thenReturn(documentDog);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/document_dog")
                        .content(documentDogObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.typeDocumentDog").value(String.valueOf(typeDocumentDog)))
                .andExpect(jsonPath("$.text").value(newText));

    }

    @Test
    void deleteDocumentDog() throws Exception{
        final TypesDocuments typeDocumentDog = TypesDocuments.DOCUMENTS_TO_ADOPT_ANIMAL;
        final String text = "Тестовый документ";

        DocumentDog documentDog = new DocumentDog();
        documentDog.setTypeDocumentDog(typeDocumentDog);
        documentDog.setText(text);

        when(documentDogRepository.findDocumentDogByTypeDocumentDog(any(TypesDocuments.class))).thenReturn(documentDog);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/document_dog/{typeDocumentDog}", typeDocumentDog)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(documentDogRepository, atLeastOnce()).deleteDocumentDogByTypeDocumentDog(typeDocumentDog);


    }
}