package com.example.finalproject.service;

import com.example.finalproject.dto.ElectionDTO;

import java.util.List;

public interface IElectionService {

    ElectionDTO getElection(Integer electionId);

    List<ElectionDTO> getElectionsByStudentId();

    List<ElectionDTO> getElections();

    ElectionDTO getElectionResult(Integer electionId);

    ElectionDTO newElection(ElectionDTO electionDTO);
}

