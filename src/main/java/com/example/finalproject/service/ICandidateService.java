package com.example.finalproject.service;

import com.example.finalproject.dto.CandidateDTO;

import java.util.List;

public interface ICandidateService {

    CandidateDTO submit(CandidateDTO candidateDTO);

    List<CandidateDTO> getCandidatesByElectionId(int electionId);

    List<CandidateDTO> findAll();
}

