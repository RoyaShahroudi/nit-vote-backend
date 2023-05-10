package com.example.finalproject.mapper;

import com.example.finalproject.domain.Election;
import com.example.finalproject.dto.ElectionDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ElectionMapper {
    public Election toEntity(ElectionDTO electionDTO) {
        if (Objects.isNull(electionDTO)) return null;
        return new Election()
                .setId(electionDTO.getId())
                .setName(electionDTO.getName())
                .setStartDate(electionDTO.getStartDate())
                .setEndDate(electionDTO.getEndDate())
                .setRequirements(electionDTO.getRequirements());
    }

    public ElectionDTO toDTO(Election election) {
        if (Objects.isNull(election)) return null;
        return new ElectionDTO()
                .setId(election.getId())
                .setName(election.getName())
                .setStartDate(election.getStartDate())
                .setEndDate(election.getEndDate())
                .setRequirements(election.getRequirements());
    }

}
