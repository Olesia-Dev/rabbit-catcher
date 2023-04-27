package com.zaiats.catcher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaiats.catcher.exception.ResourceNotFoundException;
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
    private final CandidateModel candidateModel = createCandidateModelWithCustomId(candidateId);
    private final CandidateModel candidateModelNoId = createCandidateModelWithCustomId(null);

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
    void getCandidateById_userNotFound_returns404() throws Exception {
        when(mockCandidateService.getCandidateById(anyLong()))
                .thenThrow(new ResourceNotFoundException("Id is not in the system!"));

        mockMvc.perform(get("/candidates/{id}", candidateId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Id is not in the system!")));

        verify(mockCandidateService).getCandidateById(candidateId);
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

        verify(mockCandidateService).saveCandidate(candidateModelNoId);
    }

    @Test
    void createCandidate_requiredDataOnly_returns201() throws Exception {
        CandidateModel minimalCandidateModel = getMinimalCandidateModel();

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
        CandidateModel noFirstNameCandidate = getMinimalCandidateModel();
        noFirstNameCandidate.setFirstName(null);

        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noFirstNameCandidate)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("First name can't be empty.")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void createCandidate_lastNameIsAbsent_returns400() throws Exception {
        CandidateModel noLastNameCandidate = getMinimalCandidateModel();
        noLastNameCandidate.setLastName(null);

        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noLastNameCandidate)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Last name can't be empty.")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void createCandidate_emailIsAbsent_returns400() throws Exception {
        CandidateModel noEmailCandidate = getMinimalCandidateModel();
        noEmailCandidate.setEmail(null);

        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noEmailCandidate)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Email can't be empty.")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void createCandidate_firstNameIsTooLong_returns400() throws Exception {
        CandidateModel longFirstNameCandidate = getMinimalCandidateModel();
        longFirstNameCandidate.setFirstName(
                "Stepan is very polite boy, but he doesn't have to have more than sixty four characters");

        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(longFirstNameCandidate)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",
                        containsString("First name should be no longer than 64 characters.")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void createCandidate_lastNameIsTooLong_returns400() throws Exception {
        CandidateModel longLastNameCandidate = getMinimalCandidateModel();
        longLastNameCandidate.setLastName(
                "Dudka is very polite boy, but he doesn't have to have more than sixty four characters");

        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(longLastNameCandidate)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",
                        containsString("Last name should be no longer than 64 characters.")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void createCandidate_emailIsInvalid_returns400() throws Exception {
        CandidateModel invalidEmailCandidate = getMinimalCandidateModel();
        invalidEmailCandidate.setEmail("invalid.email");

        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmailCandidate)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Email is invalid.")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void createCandidate_tooLongCurrentPosition_returns400() throws Exception {
        CandidateModel tooLongCurrentPositionCandidate = getMinimalCandidateModel();
        tooLongCurrentPositionCandidate.setCurrentPosition("Candidate with very long current position that is" +
                " actually have no value to me, but I need to test this oversized position title. Stepan Dudka is " +
                "very polite, but I don't know him. I want to know more about his current position for our new" +
                " opportunity. I'm waiting for his current position.");

        mockMvc.perform(post("/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tooLongCurrentPositionCandidate)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",
                        containsString("Current position title should be no longer than 256 characters.")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void updateCandidate_returns200() throws Exception {
        when(mockCandidateService.updateCandidate(candidateId, candidateModelNoId))
                .thenReturn(candidateModel);

        mockMvc.perform(put("/candidates/{id}", candidateId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateModel)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(candidateModel)))
                .andReturn();

        verify(mockCandidateService).updateCandidate(candidateId, candidateModelNoId);
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

    @Test
    void searchByEmail_returns200() throws Exception {
        when(mockCandidateService.searchByEmail(anyString()))
                .thenReturn(candidateModel);

        mockMvc.perform(get("/candidates/search/{email}", "test.mail@email.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(candidateModel)))
                .andReturn();

        verify(mockCandidateService).searchByEmail("test.mail@email.com");
    }

    @Test
    void searchByEmail_invalidEmail_returns400() throws Exception {
        mockMvc.perform(get("/candidates/search/{email}", "invalid.email")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("searchByEmail.email: must be a well-formed email address")));

        verifyNoInteractions(mockCandidateService);
    }

    @Test
    void searchByEmail_userNotFound_returns404() throws Exception {
        when(mockCandidateService.searchByEmail(anyString()))
                .thenThrow(new ResourceNotFoundException("Email is not in the system!"));

        mockMvc.perform(get("/candidates/search/{email}", "test.mail@email.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Email is not in the system!")));

        verify(mockCandidateService).searchByEmail("test.mail@email.com");
    }

    private CandidateModel getMinimalCandidateModel() {
        CandidateModel minimalCandidateModel = new CandidateModel();
        minimalCandidateModel.setFirstName("Stepan");
        minimalCandidateModel.setLastName("Dudka");
        minimalCandidateModel.setEmail("stepan.dudka@mail.com");
        return minimalCandidateModel;
    }

    private CandidateModel createCandidateModelWithCustomId(Long id) {
        return new CandidateModel(id, "Alex", "Bip", "alex.bip@mail.com", "Developer");
    }

}
