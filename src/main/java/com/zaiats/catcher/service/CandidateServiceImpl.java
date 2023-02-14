package com.zaiats.catcher.service;

import com.zaiats.catcher.exception.ResourceNotFoundException;
import com.zaiats.catcher.model.CandidateModel;
import com.zaiats.catcher.repository.CandidateRepository;
import com.zaiats.catcher.repository.entity.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public List<CandidateModel> getAllCandidates() {
        List<Candidate> candidates = candidateRepository.findAll();
        return candidates.stream()
                .map(CandidateModel::fromEntity)
                .toList();
    }

    @Override
    public CandidateModel getCandidateById(Long id) {
        Candidate foundCandidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate with ID: " + id + " not found!"));
        return CandidateModel.fromEntity(foundCandidate);
    }

    @Override
    public CandidateModel updateCandidate(Long id, CandidateModel candidateModel) {
        Candidate candidate = CandidateModel.toEntity(candidateModel);
        candidate.setId(id);
        return CandidateModel.fromEntity(candidateRepository.save(candidate));
    }

    @Override
    public CandidateModel saveCandidate(CandidateModel candidateModel) {
        Candidate candidate = CandidateModel.toEntity(candidateModel);
        return CandidateModel.fromEntity(candidateRepository.save(candidate));
    }

    @Override
    public void removeById(Long id) {
        candidateRepository.deleteById(id);
    }

}
