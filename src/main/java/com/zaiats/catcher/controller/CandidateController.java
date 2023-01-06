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
    @ResponseStatus(HttpStatus.OK)
    public Map<Integer, String> getAllCandidates() {
        return candidateService.getIdToCandidateCollection();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String getCandidate(@PathVariable Integer id) {
        return candidateService.getCandidateById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createCandidate(@RequestBody String candidate) {
        candidateService.addNewCandidate(candidate);
        return candidate;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateCandidate(@PathVariable Integer id, @RequestBody String candidate) {
        String existingCandidate = candidateService.updateCandidate(id, candidate);
        return existingCandidate + " -> " + candidate;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeCandidateById(@PathVariable Integer id) {
        candidateService.removeById(id);
    }

}
