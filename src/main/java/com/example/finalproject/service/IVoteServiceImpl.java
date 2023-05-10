package com.example.finalproject.service;

import com.example.finalproject.dto.VoteDTO;
import com.example.finalproject.mapper.VoteMapper;
import com.example.finalproject.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IVoteServiceImpl implements IVoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteMapper voteMapper;

    @Override
    public VoteDTO submit(VoteDTO voteDTO) {
        if (voteRepository.findByStudentIdAndElectionId(voteDTO.getStudentId(), voteDTO.getElectionId()).isEmpty()) {
            return voteMapper.toDTO(voteRepository.save(voteMapper.toEntity(voteDTO)));
        }
        //TODO throw exception
        return null;
    }
}
