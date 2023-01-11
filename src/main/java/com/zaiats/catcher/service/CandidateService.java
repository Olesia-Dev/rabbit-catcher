package com.zaiats.catcher.service;

import com.zaiats.catcher.model.CandidateModel;
import com.zaiats.catcher.repository.CandidateRepository;
import com.zaiats.catcher.repository.entity.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    private static Integer sequentialId = 1;
    private static Map<Integer, CandidateModel> idToCandidateCollection = new HashMap<>();

    public List<CandidateModel> getAllCandidates() {
        List<Candidate> candidates = candidateRepository.findAll();
        List<CandidateModel> candidateModels = candidates.stream()
                .map(CandidateModel::fromEntity)
                .toList();
        return candidateModels;
    }

    public CandidateModel getCandidateById(Integer id) {
        Candidate foundCandidate = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found!"));
        return CandidateModel.fromEntity(foundCandidate);
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
        candidateRepository.deleteById(id);
    }

}
