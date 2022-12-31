package com.zaiats.catcher.controller;

import com.zaiats.catcher.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping
    public Map<Integer, String> getAllCandidates() {
        return candidateService.getIdToCandidateCollection();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createCandidate(@RequestBody String candidate) {
        candidateService.addNewCandidate(candidate);
        return candidate;
    }

    @DeleteMapping("/{id}")
    public void removeCandidateById(@PathVariable Integer id) {
        candidateService.removeById(id);
    }

}
