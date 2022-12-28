package com.zaiats.catcher.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CandidateService {

    private List<String> candidateCollection = new ArrayList<>();

    public CandidateService() {
        initMockData();
    }

    private void initMockData() {
        candidateCollection.addAll(List.of("Alex Bip", "Igor Papka", "Iryna Chek"));
    }

    public List<String> getCandidateCollection() {
        return this.candidateCollection;
    }

    public void removeFirst() {
        candidateCollection.remove(0);
    }

}
