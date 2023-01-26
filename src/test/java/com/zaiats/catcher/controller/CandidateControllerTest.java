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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CandidateController.class)
class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CandidateService candidateService;

    private final CandidateModel candidateModel = new CandidateModel(
            20230122222130L, "Alex", "Bip", "alex.bip@mail.com", "Developer");

    @Test
    void getAllCandidates_returns200() throws Exception {
        when(candidateService.getAllCandidates())
                .thenReturn(List.of(candidateModel));

        mockMvc.perform(get("/candidates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        objectMapper.writeValueAsString(List.of(candidateModel))))
                .andReturn();

        verify(candidateService).getAllCandidates();
    }

    @Test
    void getCandidate_returns200() throws Exception {
        when(candidateService.getCandidateById(20230122222130L))
                .thenReturn(candidateModel);

        mockMvc.perform(get("/candidates/{id}", 20230122222130L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(candidateModel)))
                .andReturn();

        verify(candidateService).getCandidateById(20230122222130L);
    }

    @Test
    void createCandidate_returns201() throws Exception {
        when(candidateService.saveCandidate(isA(CandidateModel.class)))
                .thenReturn(candidateModel);

        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateModel)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(candidateModel)))
                .andReturn();

        verify(candidateService).saveCandidate(candidateModel);
    }

    @Test
    void updateCandidate_returns200() throws Exception {
        when(candidateService.updateCandidate(20230122222130L, candidateModel))
                .thenReturn(candidateModel);

        mockMvc.perform(put("/candidates/{id}", 20230122222130L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateModel)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(candidateModel)))
                .andReturn();

        verify(candidateService).updateCandidate(20230122222130L, candidateModel);
    }

    @Test
    void removeCandidateById_returns204() throws Exception {
        mockMvc.perform(delete("/candidates/{id}", 20230122222130L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(candidateService).removeById(20230122222130L);
    }
    
}
