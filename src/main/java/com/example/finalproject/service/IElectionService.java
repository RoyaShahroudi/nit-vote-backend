package com.example.finalproject.service;

import com.example.finalproject.dto.AddCandidateGroupRequestDTO;
import com.example.finalproject.dto.ElectionDTO;
import com.example.finalproject.dto.ElectionResult;

import java.util.List;

public interface IElectionService {

    ElectionDTO getElection(Integer electionId);

    List<ElectionDTO> getElectionsByStudentId();

    List<ElectionDTO> getElections();

    ElectionResult getElectionResult(Integer electionId);

    ElectionResult getElectionResultForStudent(Integer electionId);

    ElectionDTO newElection(ElectionDTO electionDTO);

    void addCandidateToElection(AddCandidateGroupRequestDTO addCandidateGroupRequestDTO);
}

