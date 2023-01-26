package com.zaiats.catcher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaiats.catcher.model.CandidateModel;
import com.zaiats.catcher.service.CandidateService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private CandidateModel candidateModel = new CandidateModel(
            20230122222130L, "Alex", "Bip", "alex.bip@mail.com", "Developer");

    @Test
    void getAllCandidates_returns200() throws Exception {
        when(candidateService.getAllCandidates()).thenReturn(List.of(candidateModel));
        MvcResult mvcResult = mockMvc.perform(get("/candidates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expectedResponseBody = objectMapper.writeValueAsString(List.of(candidateModel));
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponseBody, actualResponseBody);
    }

    @Test
    void getCandidate_returns200() throws Exception {
        when(candidateService.getCandidateById(20230122222130L)).thenReturn(candidateModel);
        MvcResult mvcResult = mockMvc.perform(get("/candidates/20230122222130")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expectedResponseBody = objectMapper.writeValueAsString(candidateModel);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponseBody, actualResponseBody);
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

        mockMvc.perform(put("/candidates/20230122222130")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateModel)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(candidateModel)))
                .andReturn();

        verify(candidateService).updateCandidate(20230122222130L, candidateModel);
    }

    @Disabled // TODO
    @Test
    void removeCandidateById() {
    }

}
