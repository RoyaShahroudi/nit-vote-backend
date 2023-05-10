package com.example.finalproject.mapper;

import com.example.finalproject.domain.CandidateGroup;
import com.example.finalproject.dto.CandidateGroupDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CandidateGroupMapper {
    public CandidateGroup toEntity(CandidateGroupDTO candidateGroupDTO) {
        if (Objects.isNull(candidateGroupDTO)) return null;
        return new CandidateGroup()
                .setId(candidateGroupDTO.getId())
                .setCandidate(candidateGroupDTO.getCandidate())
                .setElection(candidateGroupDTO.getElection())
                .setVoteCount(candidateGroupDTO.getVoteCount());
    }

    public CandidateGroupDTO toDTO(CandidateGroup candidateGroup) {
        if (Objects.isNull(candidateGroup)) return null;
        return new CandidateGroupDTO()
                .setId(candidateGroup.getId())
                .setCandidate(candidateGroup.getCandidate())
                .setElection(candidateGroup.getElection())
                .setVoteCount(candidateGroup.getVoteCount());
    }

}
