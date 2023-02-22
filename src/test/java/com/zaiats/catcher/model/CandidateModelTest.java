package com.zaiats.catcher.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

class CandidateModelTest {

    @Test
    public void testSerialization() throws IOException, JSONException {
        var expected = IOUtils.resourceToString("/data/candidate.json", StandardCharsets.UTF_8);
        CandidateModel candidateModel = new CandidateModel();
        candidateModel.setId(20230221L);
        candidateModel.setFirstName("Rebecca");
        candidateModel.setLastName("Bocelli");
        candidateModel.setEmail("test@mail.com");
        candidateModel.setCurrentPosition("Developer");
        var actual = new ObjectMapper().writeValueAsString(candidateModel);

        JSONAssert.assertEquals(expected, actual, true);
    }

}
