package com.example.finalproject.controller;

import com.example.finalproject.dto.CandidateDTO;
import com.example.finalproject.service.ICandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/v1/candidate")
public class CandidateController {

    @Autowired
    private ICandidateService candidateService;

    @PostMapping("/submit")
    public ResponseEntity<CandidateDTO> submit(@RequestBody @Valid CandidateDTO candidate) {
        return new ResponseEntity<>(candidateService.submit(candidate), HttpStatus.CREATED);
    }

    @GetMapping("/by-election-id/{electionId}")
    public ResponseEntity<List<CandidateDTO>> getCandidatesByElectionId(@PathVariable Integer electionId) {
        return new ResponseEntity<>(candidateService.getCandidatesByElectionId(electionId), HttpStatus.OK);
    }
}
