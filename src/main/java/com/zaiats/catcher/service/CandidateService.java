package com.zaiats.catcher.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CandidateService {

    private static Integer sequentialId = 1;
    private static Map<Integer, String> idToCandidateCollection = new HashMap<>();

    public CandidateService() {
        initMockData();
    }

    private void initMockData() {
        idToCandidateCollection.putAll(Map.of(
                getNextId(), "Alex Bip",
                getNextId(), "Igor Papka",
                getNextId(), "Iryna Chek"));
    }

    public Map<Integer, String> getIdToCandidateCollection() {
        return idToCandidateCollection;
    }

    public void addNewCandidate(String candidate) {
        idToCandidateCollection.put(getNextId(), candidate);
    }

    public void removeById(Integer id) {
        idToCandidateCollection.remove(id);
    }

    private Integer getNextId() {
        return sequentialId++;
    }

}
