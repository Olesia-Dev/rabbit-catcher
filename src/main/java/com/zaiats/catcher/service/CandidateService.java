package com.zaiats.catcher.service;

import com.zaiats.catcher.model.CandidateModel;
import com.zaiats.catcher.repository.CandidateRepository;
import com.zaiats.catcher.repository.entity.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public List<CandidateModel> getAllCandidates() {
        List<Candidate> candidates = candidateRepository.findAll();
        List<CandidateModel> candidateModels = candidates.stream()
                .map(CandidateModel::fromEntity)
                .toList();
        return candidateModels;
    }

    public CandidateModel getCandidateById(Long id) {
        Candidate foundCandidate = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found!"));
        return CandidateModel.fromEntity(foundCandidate);
    }

    public CandidateModel updateCandidate(Long id, CandidateModel candidateModel) {
        Candidate candidate = CandidateModel.toEntity(candidateModel);
        candidate.setId(id);
        return CandidateModel.fromEntity(candidateRepository.save(candidate));
    }

    public CandidateModel saveCandidate(CandidateModel candidateModel) {
        Candidate candidate = CandidateModel.toEntity(candidateModel);
        return CandidateModel.fromEntity(candidateRepository.save(candidate));
    }

    public void removeById(Long id) {
        candidateRepository.deleteById(id);
    }

}
