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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                        .contentType("application/json"))
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
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String expectedResponseBody = objectMapper.writeValueAsString(candidateModel);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponseBody, actualResponseBody);
    }

    @Disabled // TODO
    @Test
    void createCandidate() {
    }

    @Disabled // TODO
    @Test
    void updateCandidate() {
    }

    @Disabled // TODO
    @Test
    void removeCandidateById() {
    }

}
