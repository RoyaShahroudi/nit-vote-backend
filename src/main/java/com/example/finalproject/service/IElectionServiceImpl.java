package com.example.finalproject.service;

import com.example.finalproject.domain.CandidateGroup;
import com.example.finalproject.domain.Election;
import com.example.finalproject.dto.*;
import com.example.finalproject.exceptions.messages.CandidateGroupDuplicateException;
import com.example.finalproject.exceptions.messages.CandidateNotFoundException;
import com.example.finalproject.exceptions.messages.ElectionNotFoundException;
import com.example.finalproject.exceptions.messages.InvalidDateException;
import com.example.finalproject.mapper.CandidateGroupMapper;
import com.example.finalproject.mapper.CandidateMapper;
import com.example.finalproject.mapper.ElectionMapper;
import com.example.finalproject.repository.CandidateGroupRepository;
import com.example.finalproject.repository.CandidateRepository;
import com.example.finalproject.repository.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class IElectionServiceImpl implements IElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private ElectionMapper electionMapper;

    @Autowired
    private CandidateMapper candidateMapper;

    @Autowired
    private CandidateGroupMapper candidateGroupMapper;

    @Autowired
    private CandidateGroupRepository candidateGroupRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public ElectionDTO getElection(Integer electionId) {
        //TODO throw exception
        return electionMapper.toDTO(electionRepository.findById(electionId).orElse(null));
    }


    @Override
    public List<ElectionDTO> getElectionsByStudentId() {
        List<Election> elections = electionRepository.findAll();
        Date date = new Date();
        return elections.stream().filter(election -> election.getStartDate().before(date) && election.getEndDate().after(date)).map(electionMapper::toDTO).toList();
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
        if(Objects.isNull(electionDTO.getStartDate()) || Objects.isNull(electionDTO.getEndDate())) {
            throw new InvalidDateException();
        }
        return electionMapper.toDTO(electionRepository.save(electionMapper.toEntity(electionDTO)));
    }

    @Override
    public void addCandidateToElection(AddCandidateGroupRequestDTO candidateGroupDTO) {
        if (!candidateGroupRepository.findAllByElectionIdAndCandidateId(candidateGroupDTO.getElectionId(), candidateGroupDTO.getCandidateId()).isEmpty()) {
            throw new CandidateGroupDuplicateException();
        }
        candidateGroupRepository.save(new CandidateGroup()
                .setElection(electionRepository.findById(candidateGroupDTO.getElectionId())
                        .orElseThrow(ElectionNotFoundException::new))
                .setCandidate(candidateRepository.findById(candidateGroupDTO.getCandidateId())
                        .orElseThrow(CandidateNotFoundException::new)));
    }
}
