package com.zaiats.catcher.controller;

import com.zaiats.catcher.model.CandidateModel;
import com.zaiats.catcher.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
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
    public CandidateModel getCandidate(@PathVariable @Min(1) Long id) {
        return candidateService.getCandidateById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CandidateModel createCandidate(@RequestBody @Valid CandidateModel candidateModel) {
        return candidateService.saveCandidate(candidateModel);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CandidateModel updateCandidate(@PathVariable @Min(1) Long id,
                                          @RequestBody @Valid CandidateModel candidateModel) {
        return candidateService.updateCandidate(id, candidateModel);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCandidateById(@PathVariable @Min(1) Long id) {
        candidateService.removeById(id);
    }

    @GetMapping("/search/{email}")
    @ResponseStatus(HttpStatus.OK)
    public CandidateModel searchByEmail(@PathVariable @Email String email) {
        return candidateService.searchByEmail(email);
    }

}
