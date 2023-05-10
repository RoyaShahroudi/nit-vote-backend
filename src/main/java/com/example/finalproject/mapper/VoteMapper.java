package com.example.finalproject.mapper;

import com.example.finalproject.domain.Vote;
import com.example.finalproject.dto.VoteDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VoteMapper {
    public Vote toEntity(VoteDTO voteDTO) {
        if (Objects.isNull(voteDTO)) return null;
        return new Vote()
                .setId(voteDTO.getId())
                .setStudentId(voteDTO.getStudentId())
                .setElectionId(voteDTO.getElectionId());
    }

    public VoteDTO toDTO(Vote vote) {
        if (Objects.isNull(vote)) return null;
        return new VoteDTO()
                .setId(vote.getId())
                .setStudentId(vote.getStudentId())
                .setElectionId(vote.getElectionId());
    }

}
