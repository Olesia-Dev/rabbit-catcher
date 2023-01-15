package com.zaiats.catcher.controller;

import com.zaiats.catcher.model.CandidateModel;
import com.zaiats.catcher.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CandidateModel> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CandidateModel getCandidate(@PathVariable Long id) {
        return candidateService.getCandidateById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CandidateModel createCandidate(@RequestBody CandidateModel candidateModel) {
        return candidateService.saveCandidate(candidateModel);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CandidateModel updateCandidate(@PathVariable Long id, @RequestBody CandidateModel candidateModel) {
        return candidateService.updateCandidate(id, candidateModel);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCandidateById(@PathVariable Long id) {
        candidateService.removeById(id);
    }

}
