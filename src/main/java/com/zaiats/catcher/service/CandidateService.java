package com.zaiats.catcher.service;

import com.zaiats.catcher.model.CandidateModel;
import com.zaiats.catcher.repository.CandidateRepository;
import com.zaiats.catcher.repository.entity.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    private static Integer sequentialId = 1;
    private static Map<Integer, CandidateModel> idToCandidateCollection = new HashMap<>();

    public CandidateService() {
        initMockData();
    }

    private void initMockData() {
        addNewCandidate(new CandidateModel("Olesia", "Kuku", "test1@mail.com"));
        addNewCandidate(new CandidateModel("Alex", "Byk", "test2@mail.com"));
        addNewCandidate(new CandidateModel("Nicolas", "Key", "test3@mail.com"));
    }

    public Map<Integer, CandidateModel> getIdToCandidateCollection() {
        saveCandidate(new Candidate(1, "Olesia", "Kuku", "test1@mail.com"));
        saveCandidate(new Candidate(2, "Alex", "Byk", "test2@mail.com"));
        saveCandidate(new Candidate(3, "Nicolas", "Key", "test3@mail.com"));
        return idToCandidateCollection;
    }

    public CandidateModel getCandidateById(Integer id) {
        return idToCandidateCollection.get(id);
    }

    public CandidateModel updateCandidate(Integer id, CandidateModel candidateModel) {
        return idToCandidateCollection.put(id, candidateModel);
    }

    public void addNewCandidate(CandidateModel candidateModel) {
        idToCandidateCollection.put(sequentialId++, candidateModel);
    }

    public void saveCandidate(Candidate candidate) {
        candidateRepository.save(candidate);
    }

    public void removeById(Integer id) {
        idToCandidateCollection.remove(id);
    }

}
