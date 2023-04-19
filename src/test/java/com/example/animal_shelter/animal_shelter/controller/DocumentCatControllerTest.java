package com.example.animal_shelter.animal_shelter.controller;

import com.example.animal_shelter.animal_shelter.model.DocumentCat;
import com.example.animal_shelter.animal_shelter.model.TypesDocuments;
import com.example.animal_shelter.animal_shelter.repository.DocumentCatRepository;
import com.example.animal_shelter.animal_shelter.service.DocumentCatService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DocumentCatController.class)
class DocumentCatControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DocumentCatRepository documentCatRepository;

    @SpyBean
    private DocumentCatService documentCatService;

    @InjectMocks
    private DocumentCatController documentCatController;


    @Test
    void createDocumentCat() throws Exception{
        final TypesDocuments typeDocument = TypesDocuments.DOCUMENTS_TO_ADOPT_ANIMAL;
        final String text = "Тестовый документ";

        JSONObject documentCatObject = new JSONObject();
        documentCatObject.put("typeDocumentCat",typeDocument);
        documentCatObject.put("text",text);

        DocumentCat documentCat = new DocumentCat(typeDocument,text);

        when(documentCatRepository.save(any(DocumentCat.class))).thenReturn(documentCat);
        when(documentCatRepository.findDocumentCatByTypeDocumentCat(any(TypesDocuments.class))).thenReturn(documentCat);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/document_cat")
                        .content(documentCatObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.typeDocumentCat").value(String.valueOf(typeDocument)))
                .andExpect(jsonPath("$.text").value(text));

    }

    @Test
    void editDocumentCat() throws Exception{
        final TypesDocuments typeDocument = TypesDocuments.DOCUMENTS_TO_ADOPT_ANIMAL;
        final String text = "Тестовый документ";

        final TypesDocuments newTypeDocument= TypesDocuments.DOCUMENTS_TO_ADOPT_ANIMAL;
        final String newText = "Тестовый документ новый";

        JSONObject documentCatObject = new JSONObject();
        documentCatObject.put("typeDocumentCat",typeDocument);
        documentCatObject.put("text",text);

        DocumentCat documentCat= new DocumentCat(typeDocument,text);
        DocumentCat newDocumentCat = new DocumentCat(newTypeDocument,newText);

        when(documentCatRepository.save(any(DocumentCat.class))).thenReturn(newDocumentCat);
        when(documentCatRepository.findDocumentCatByTypeDocumentCat(any(TypesDocuments.class))).thenReturn(documentCat);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/document_cat")
                        .content(documentCatObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.typeDocumentCat").value(String.valueOf(typeDocument)))
                .andExpect(jsonPath("$.text").value(newText));
    }

    @Test
    void deleteDocumentCat() throws Exception{
        final TypesDocuments typeDocument = TypesDocuments.DOCUMENTS_TO_ADOPT_ANIMAL;
        final String text = "Тестовый документ";

        DocumentCat documentCat = new DocumentCat();
        documentCat.setTypeDocumentCat(typeDocument);
        documentCat.setText(text);

        when(documentCatRepository.findDocumentCatByTypeDocumentCat(any(TypesDocuments.class))).thenReturn(documentCat);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/document_cat/{typeDocumentCat}", typeDocument)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(documentCatRepository, atLeastOnce()).deleteDocumentCatByTypeDocumentCat(typeDocument);
    }
}