package com.example.finalproject.dto;

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
public class AddCandidateGroupRequestDTO {

    @JsonIgnore
    private Integer id;

    @NotNull
    private Integer electionId;

    @NotNull
    private Integer candidateId;

    private Integer voteCount;
}
