package com.zaiats.catcher.controller;

import com.zaiats.catcher.model.Candidate;
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
    @ResponseStatus(HttpStatus.OK)
    public Map<Integer, Candidate> getAllCandidates() {
        return candidateService.getIdToCandidateCollection();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Candidate getCandidate(@PathVariable Integer id) {
        return candidateService.getCandidateById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Candidate createCandidate(@RequestBody Candidate candidate) {
        candidateService.addNewCandidate(candidate);
        return candidate;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateCandidate(@PathVariable Integer id, @RequestBody Candidate candidate) {
        Candidate existingCandidate = candidateService.updateCandidate(id, candidate);
        return existingCandidate + "\n->\n" + candidate;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeCandidateById(@PathVariable Integer id) {
        candidateService.removeById(id);
    }

}
