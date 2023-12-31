package com.example.finalproject.service;

import com.example.finalproject.dto.AddCandidateGroupRequestDTO;
import com.example.finalproject.dto.CandidateDTO;
import com.example.finalproject.dto.ElectionDTO;
import com.example.finalproject.dto.ElectionResult;

import java.util.List;

public interface IElectionService {

    ElectionDTO getElection(Integer electionId);

    ElectionDTO getElectionForStudent(Integer electionId);

    List<ElectionDTO> getElectionsByStudentId();

    List<ElectionDTO> getElections();

    ElectionResult getElectionResult(Integer electionId);

    ElectionResult getElectionResultForStudent(Integer electionId);

    ElectionDTO newElection(ElectionDTO electionDTO);

    void addCandidateToElection(AddCandidateGroupRequestDTO addCandidateGroupRequestDTO);

    List<CandidateDTO> getCandidatesForStudent(Integer electionId);

    List<ElectionDTO> getInProgressElections();

    List<ElectionDTO> getElectionsHistoryForStudent();

}

