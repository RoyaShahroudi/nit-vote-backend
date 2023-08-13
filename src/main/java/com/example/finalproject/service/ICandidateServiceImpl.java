package com.example.finalproject.service;

import com.example.finalproject.domain.CandidateGroup;
import com.example.finalproject.dto.CandidateDTO;
import com.example.finalproject.mapper.CandidateMapper;
import com.example.finalproject.repository.CandidateGroupRepository;
import com.example.finalproject.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ICandidateServiceImpl implements ICandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private CandidateMapper candidateMapper;

    @Autowired
    private CandidateGroupRepository candidateGroupRepository;

    @Override
    public CandidateDTO submit(CandidateDTO candidateDTO) {
        return candidateMapper.toDTO(candidateRepository.save(candidateMapper.toEntity(candidateDTO)));
    }

    @Override
    public List<CandidateDTO> getCandidatesByElectionId(int electionId) {
        List<CandidateGroup> candidateGroups = candidateGroupRepository.findAllByElectionId(electionId);
        return candidateGroups
                .stream()
                .map(candidateGroup -> candidateMapper.toDTO(candidateGroup.getCandidate())).toList();
    }

    @Override
    public List<CandidateDTO> findAll() {
        return candidateRepository.findAll().stream().map(candidateMapper::toDTO).toList();
    }
}
