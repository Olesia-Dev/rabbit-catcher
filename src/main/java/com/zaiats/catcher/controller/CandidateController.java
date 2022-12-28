package com.zaiats.catcher.controller;

import com.zaiats.catcher.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @DeleteMapping
    public void removeFirst(){
        candidateService.removeFirst();
    }

}
