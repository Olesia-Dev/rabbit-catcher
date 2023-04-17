package com.zaiats.catcher.service;

import com.zaiats.catcher.model.CandidateModel;

import java.util.List;

public interface CandidateService {

    List<CandidateModel> getAllCandidates();

    CandidateModel getCandidateById(Long id);

    CandidateModel updateCandidate(Long id, CandidateModel candidateModel);

    CandidateModel saveCandidate(CandidateModel candidateModel);

    void removeById(Long id);

    CandidateModel searchByEmail(String email);

}
