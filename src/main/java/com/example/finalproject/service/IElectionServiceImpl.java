package com.example.finalproject.service;

import com.example.finalproject.domain.CandidateGroup;
import com.example.finalproject.dto.CandidateResult;
import com.example.finalproject.dto.ElectionDTO;
import com.example.finalproject.dto.ElectionResult;
import com.example.finalproject.mapper.CandidateMapper;
import com.example.finalproject.mapper.ElectionMapper;
import com.example.finalproject.repository.CandidateGroupRepository;
import com.example.finalproject.repository.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IElectionServiceImpl implements IElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private ElectionMapper electionMapper;

    @Autowired
    private CandidateMapper candidateMapper;

    @Autowired
    private CandidateGroupRepository candidateGroupRepository;

    @Override
    public ElectionDTO getElection(Integer electionId) {
        //TODO throw exception
        return electionMapper.toDTO(electionRepository.findById(electionId).orElse(null));
    }


    //TODO need uni database
    @Override
    public List<ElectionDTO> getElectionsByStudentId() {
        return null;
    }

    @Override
    public List<ElectionDTO> getElections() {
        return electionRepository.findAll().stream().map(election -> electionMapper.toDTO(election)).toList();
    }

    @Override
    public ElectionResult getElectionResult(Integer electionId) {
        List<CandidateGroup> candidateGroups = candidateGroupRepository.findAllByElectionId(electionId);
        List<CandidateResult> response = new ArrayList<>();
        for (CandidateGroup candidateGroup : candidateGroups) {
            CandidateResult candidateResult = new CandidateResult();
            candidateResult.setCandidate(candidateMapper.toDTO(candidateGroup.getCandidate()));
            candidateResult.setVoteCount(candidateGroup.getVoteCount());
            response.add(candidateResult);
        }
        return new ElectionResult().setCandidateResults(response);
    }

    @Override
    public ElectionDTO newElection(ElectionDTO electionDTO) {
        return electionMapper.toDTO(electionRepository.save(electionMapper.toEntity(electionDTO)));

    }
}
