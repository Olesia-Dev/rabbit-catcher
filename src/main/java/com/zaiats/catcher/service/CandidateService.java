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
        addNewCandidate("Alex Bip");
        addNewCandidate("Igor Papka");
        addNewCandidate("Iryna Chek");
    }

    public Map<Integer, String> getIdToCandidateCollection() {
        return idToCandidateCollection;
    }

    public String getCandidateById(Integer id){
        return idToCandidateCollection.get(id);
    }

    public void addNewCandidate(String candidate) {
        idToCandidateCollection.put(sequentialId++, candidate);
    }

    public void removeById(Integer id) {
        idToCandidateCollection.remove(id);
    }

}
