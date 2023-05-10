package com.example.finalproject.dto;

import com.example.finalproject.domain.Candidate;
import com.example.finalproject.domain.Election;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Accessors(chain = true)
public class CandidateGroupDTO {

    @JsonIgnore
    private Integer id;

    @NotNull
    private Election election;

    @NotNull
    private Candidate candidate;

    private Integer voteCount;
}
