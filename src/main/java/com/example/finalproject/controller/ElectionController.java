package com.example.finalproject.controller;

import com.example.finalproject.dto.AddCandidateGroupRequestDTO;
import com.example.finalproject.dto.ElectionDTO;
import com.example.finalproject.dto.ElectionResult;
import com.example.finalproject.service.IElectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/v1/election")
public class ElectionController {

    @Autowired
    private IElectionService electionService;

    @GetMapping("/all")
    public ResponseEntity<List<ElectionDTO>> getElections() {
        return new ResponseEntity<>(electionService.getElections(), HttpStatus.OK);
    }

    @GetMapping("/by-student-id")
    public ResponseEntity<List<ElectionDTO>> getElectionsByStudentId() {
        return new ResponseEntity<>(electionService.getElectionsByStudentId(), HttpStatus.OK);
    }

    @GetMapping("/{electionId}")
    public ResponseEntity<ElectionDTO> getElection(@PathVariable Integer electionId) {
        return new ResponseEntity<>(electionService.getElection(electionId), HttpStatus.OK);
    }

    @GetMapping("/result/{electionId}")
    public ResponseEntity<ElectionResult> getElectionResult(@PathVariable Integer electionId) {
        return new ResponseEntity<>(electionService.getElectionResult(electionId), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<ElectionDTO> newElection(@RequestBody @Valid ElectionDTO electionDTO) {
        return new ResponseEntity<>(electionService.newElection(electionDTO), HttpStatus.CREATED);
    }

    @PostMapping("/add-candidate-to-election")
    public ResponseEntity<Void> addCandidateToElection(@RequestBody @Valid AddCandidateGroupRequestDTO candidateGroupDTO) {
        electionService.addCandidateToElection(candidateGroupDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
