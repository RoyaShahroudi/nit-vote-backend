package com.example.finalproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Accessors(chain = true)
public class ElectionDTO {

    private Integer id;

    @NotNull
    private String name;

    private Date startDate;

    private Date endDate;

    private String requirements;
}
