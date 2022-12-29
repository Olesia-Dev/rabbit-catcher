package com.zaiats.catcher.controller;

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
    public List<String> getAllCandidates() {
        return candidateService.getCandidateCollection();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createCandidate(@RequestBody String candidate) {
        candidateService.addNewCandidate(candidate);
        return candidate;
    }

    @DeleteMapping
    public void removeFirst() {
        candidateService.removeFirst();
    }

}
