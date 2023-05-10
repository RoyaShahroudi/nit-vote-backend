package com.example.finalproject.service;

import com.example.finalproject.dto.ElectionDTO;
import com.example.finalproject.mapper.ElectionMapper;
import com.example.finalproject.repository.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IElectionServiceImpl implements IElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private ElectionMapper electionMapper;

    @Override
    public ElectionDTO getElection(Integer electionId) {
        //TODO throw exception
        return electionMapper.toDTO(electionRepository.findById(electionId).orElse(null));
    }

    @Override
    public List<ElectionDTO> getElectionsByStudentId() {
        return null;
    }

    @Override
    public List<ElectionDTO> getElections() {
        return electionRepository.findAll().stream().map(election -> electionMapper.toDTO(election)).toList();
    }

    @Override
    public ElectionDTO getElectionResult(Integer electionId) {
        return null;
    }

    @Override
    public ElectionDTO newElection(ElectionDTO electionDTO) {
        return electionMapper.toDTO(electionRepository.save(electionMapper.toEntity(electionDTO)));

    }
}
