package com.example.finalproject.controller;

import com.example.finalproject.dto.VoteDTO;
import com.example.finalproject.service.IVoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/v1/vote")
public class VoteController {

    @Autowired
    private IVoteService voteService;

    @PostMapping("/submit")
    public ResponseEntity<VoteDTO> submit(@RequestBody @Valid VoteDTO vote) {
        return new ResponseEntity<>(voteService.submit(vote), HttpStatus.CREATED);
    }
}
