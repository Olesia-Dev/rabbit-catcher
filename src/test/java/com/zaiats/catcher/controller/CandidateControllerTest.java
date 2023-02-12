package com.zaiats.catcher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaiats.catcher.model.CandidateModel;
import com.zaiats.catcher.service.CandidateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CandidateController.class)
class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CandidateService mockCandidateService;

    private final Long candidateId = 20230122222130L;

    private final CandidateModel candidateModel = new CandidateModel(
            candidateId, "Alex", "Bip", "alex.bip@mail.com", "Developer");

    @Test
    void getAllCandidates_returns200() throws Exception {
        when(mockCandidateService.getAllCandidates())
                .thenReturn(List.of(candidateModel));

        mockMvc.perform(get("/candidates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        objectMapper.writeValueAsString(List.of(candidateModel))))
                .andReturn();

        verify(mockCandidateService).getAllCandidates();
    }

    @Test
    void getCandidate_returns200() throws Exception {
        when(mockCandidateService.getCandidateById(candidateId))
                .thenReturn(candidateModel);

        mockMvc.perform(get("/candidates/{id}", candidateId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(candidateModel)))
                .andReturn();

        verify(mockCandidateService).getCandidateById(candidateId);
    }

    @Test
    void getCandidateById_zeroId_returns400() throws Exception {
        mockMvc.perform(get("/candidates/{id}", 0)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("id: must be greater")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void getCandidateById_negativeId_returns400() throws Exception {
        mockMvc.perform(get("/candidates/{id}", -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("id: must be greater")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void getCandidateById_invalidIdType_returns400() throws Exception {
        mockMvc.perform(get("/candidates/{id}", 'a')
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("For input string: \"a\"")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void createCandidate_returns201() throws Exception {
        when(mockCandidateService.saveCandidate(isA(CandidateModel.class)))
                .thenReturn(candidateModel);

        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateModel)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(candidateModel)))
                .andReturn();

        verify(mockCandidateService).saveCandidate(candidateModel);
    }

    @Test
    void createCandidate_requiredDataOnly_returns201() throws Exception {
        CandidateModel minimalCandidateModel = new CandidateModel();
        minimalCandidateModel.setFirstName("Stepan");
        minimalCandidateModel.setLastName("Dudka");
        minimalCandidateModel.setEmail("stepan.dudka@mail.com");

        when(mockCandidateService.saveCandidate(isA(CandidateModel.class)))
                .thenReturn(minimalCandidateModel);

        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(minimalCandidateModel)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(minimalCandidateModel)))
                .andReturn();

        verify(mockCandidateService).saveCandidate(minimalCandidateModel);
    }

    @Test
    void createCandidate_firstNameIsAbsent_returns400() throws Exception {
        CandidateModel minimalCandidateModel = new CandidateModel();
        minimalCandidateModel.setLastName("Dudka");
        minimalCandidateModel.setEmail("stepan.dudka@mail.com");

        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(minimalCandidateModel)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("First name can't be empty.")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void createCandidate_lastNameIsAbsent_returns400() throws Exception {
        CandidateModel minimalCandidateModel = new CandidateModel();
        minimalCandidateModel.setFirstName("Stepan");
        minimalCandidateModel.setEmail("stepan.dudka@mail.com");

        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(minimalCandidateModel)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Last name can't be empty.")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void createCandidate_emailIsAbsent_returns400() throws Exception {
        CandidateModel minimalCandidateModel = new CandidateModel();
        minimalCandidateModel.setFirstName("Stepan");
        minimalCandidateModel.setLastName("Dudka");

        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(minimalCandidateModel)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Email can't be empty.")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void updateCandidate_returns200() throws Exception {
        when(mockCandidateService.updateCandidate(candidateId, candidateModel))
                .thenReturn(candidateModel);

        mockMvc.perform(put("/candidates/{id}", candidateId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateModel)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(candidateModel)))
                .andReturn();

        verify(mockCandidateService).updateCandidate(candidateId, candidateModel);
    }

    @Test
    void updateCandidate_zeroId_returns400() throws Exception {
        mockMvc.perform(put("/candidates/{id}", 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateModel)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("id: must be greater")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void updateCandidate_negativeId_returns400() throws Exception {
        mockMvc.perform(put("/candidates/{id}", -1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateModel)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("id: must be greater")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void updateCandidate_invalidIdType_returns400() throws Exception {
        mockMvc.perform(put("/candidates/{id}", 'a')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateModel)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("For input string: \"a\"")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void removeCandidateById_returns204() throws Exception {
        mockMvc.perform(delete("/candidates/{id}", candidateId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(mockCandidateService).removeById(candidateId);
    }

    @Test
    void removeCandidateById_zeroId_returns400() throws Exception {
        mockMvc.perform(delete("/candidates/{id}", 0)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("id: must be greater")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void removeCandidateById_negativeId_returns400() throws Exception {
        mockMvc.perform(delete("/candidates/{id}", -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("id: must be greater")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void removeCandidateById_invalidIdType_returns400() throws Exception {
        mockMvc.perform(delete("/candidates/{id}", 'a')
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("For input string: \"a\"")));

        verifyNoInteractions(mockCandidateService);
    }

}
