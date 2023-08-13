package com.example.finalproject.service;

import com.example.finalproject.domain.CandidateGroup;
import com.example.finalproject.domain.Student;
import com.example.finalproject.domain.Vote;
import com.example.finalproject.dto.VoteRequest;
import com.example.finalproject.exceptions.messages.CandidateNotFoundException;
import com.example.finalproject.exceptions.messages.StudentAlreadyVotedException;
import com.example.finalproject.exceptions.messages.StudentNotFoundException;
import com.example.finalproject.mapper.VoteMapper;
import com.example.finalproject.repository.CandidateGroupRepository;
import com.example.finalproject.repository.StudentRepository;
import com.example.finalproject.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class IVoteServiceImpl implements IVoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteMapper voteMapper;

    @Autowired
    private CandidateGroupRepository candidateGroupRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    @Transactional
    public void submit(VoteRequest vote) {
        Student student = studentRepository.findByStudentNumber(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()).orElseThrow(StudentNotFoundException::new);
        if (voteRepository.findByStudentIdAndElectionId(student.getId(), vote.getElectionId()).isPresent()) {
            throw new StudentAlreadyVotedException();
        }
        voteRepository.save(new Vote().setElectionId(vote.getElectionId()).setStudentId(student.getId()));
        CandidateGroup candidateGroup = candidateGroupRepository.findByElectionIdAndCandidateId(vote.getElectionId(), vote.getCandidateId()).orElseThrow(CandidateNotFoundException::new);
        candidateGroup.setVoteCount(candidateGroup.getVoteCount() + 1);
        candidateGroupRepository.save(candidateGroup);
    }
}
