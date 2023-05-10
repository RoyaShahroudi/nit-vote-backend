package com.example.finalproject.mapper;

import com.example.finalproject.domain.Candidate;
import com.example.finalproject.dto.CandidateDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CandidateMapper {
    public Candidate toEntity(CandidateDTO candidateDTO) {
        if (Objects.isNull(candidateDTO)) return null;
        return new Candidate()
                .setId(candidateDTO.getId())
                .setName(candidateDTO.getName());
    }

    public CandidateDTO toDTO(Candidate candidate) {
        if (Objects.isNull(candidate)) return null;
        return new CandidateDTO()
                .setId(candidate.getId())
                .setName(candidate.getName());
    }

}
