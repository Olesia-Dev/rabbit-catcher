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
    public CandidateModel getCandidate(@PathVariable Integer id) {
        return candidateService.getCandidateById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CandidateModel createCandidate(@RequestBody CandidateModel candidateModel) {
        candidateService.addNewCandidate(candidateModel);
        return candidateModel;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateCandidate(@PathVariable Integer id, @RequestBody CandidateModel candidateModel) {
        CandidateModel existingCandidateModel = candidateService.updateCandidate(id, candidateModel);
        return existingCandidateModel + "\n->\n" + candidateModel;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeCandidateById(@PathVariable Integer id) {
        candidateService.removeById(id);
    }

}
