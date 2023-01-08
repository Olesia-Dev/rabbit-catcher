package com.zaiats.catcher.service;

import com.zaiats.catcher.model.Candidate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CandidateService {

    private static Integer sequentialId = 1;
    private static Map<Integer, Candidate> idToCandidateCollection = new HashMap<>();

    public CandidateService() {
        initMockData();
    }

    private void initMockData() {
        addNewCandidate(new Candidate("Olesia", "Kuku", "test1@mail.com"));
        addNewCandidate(new Candidate("Alex", "Byk", "test2@mail.com"));
        addNewCandidate(new Candidate("Nicolas", "Key", "test3@mail.com"));
    }

    public Map<Integer, Candidate> getIdToCandidateCollection() {
        return idToCandidateCollection;
    }

    public Candidate getCandidateById(Integer id) {
        return idToCandidateCollection.get(id);
    }

    public Candidate updateCandidate(Integer id, Candidate candidate) {
        return idToCandidateCollection.put(id, candidate);
    }

    public void addNewCandidate(Candidate candidate) {
        idToCandidateCollection.put(sequentialId++, candidate);
    }

    public void removeById(Integer id) {
        idToCandidateCollection.remove(id);
    }

}
