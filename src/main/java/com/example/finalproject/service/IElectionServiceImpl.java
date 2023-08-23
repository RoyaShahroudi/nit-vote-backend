package com.example.finalproject.service;

import com.example.finalproject.domain.CandidateGroup;
import com.example.finalproject.domain.Election;
import com.example.finalproject.domain.Student;
import com.example.finalproject.dto.*;
import com.example.finalproject.exceptions.messages.*;
import com.example.finalproject.mapper.CandidateGroupMapper;
import com.example.finalproject.mapper.CandidateMapper;
import com.example.finalproject.mapper.ElectionMapper;
import com.example.finalproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class IElectionServiceImpl implements IElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private ElectionMapper electionMapper;

    @Autowired
    private CandidateMapper candidateMapper;

    @Autowired
    private CandidateGroupRepository candidateGroupRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public ElectionDTO getElection(Integer electionId) {
        return electionMapper.toDTO(electionRepository.findById(electionId).orElseThrow(ElectionNotFoundException::new));
    }

    @Override
    public List<ElectionDTO> getElectionsByStudentId() {
        List<Election> elections = electionRepository.findAll();
        Date date = new Date();
        return elections.stream().filter(election -> election.getStartDate().before(date) && election.getEndDate().after(date)).map(electionMapper::toDTO).toList();
    }

    @Override
    public List<ElectionDTO> getElections() {
        return electionRepository.findAll().stream().map(election -> electionMapper.toDTO(election)).toList();
    }

    @Override
    public ElectionResult getElectionResult(Integer electionId) {
        List<CandidateGroup> candidateGroups = candidateGroupRepository.findAllByElectionId(electionId);
        return new ElectionResult().setCandidateResults(
                candidateGroups.stream().map(candidateGroup -> {
                    CandidateResult candidateResult = new CandidateResult();
                    candidateResult.setCandidate(candidateMapper.toDTO(candidateGroup.getCandidate()));
                    candidateResult.setVoteCount(candidateGroup.getVoteCount());
                    return candidateResult;
                }).toList());
    }

    @Override
    public ElectionResult getElectionResultForStudent(Integer electionId) {
        Election election = electionRepository.findById(electionId).orElseThrow(ElectionNotFoundException::new);
        if (election.getEndDate().after(new Date())) {
            throw new ElectionStillInProgressException();
        }
        List<CandidateGroup> candidateGroups = candidateGroupRepository.findAllByElectionId(electionId);
        return new ElectionResult().setCandidateResults(
                candidateGroups.stream().map(candidateGroup -> {
                    CandidateResult candidateResult = new CandidateResult();
                    candidateResult.setCandidate(candidateMapper.toDTO(candidateGroup.getCandidate()));
                    candidateResult.setVoteCount(candidateGroup.getVoteCount());
                    return candidateResult;
                }).toList());
    }

    @Override
    public ElectionDTO newElection(ElectionDTO electionDTO) {
        if (Objects.isNull(electionDTO.getStartDate()) || Objects.isNull(electionDTO.getEndDate())
                || electionDTO.getStartDate().after(electionDTO.getEndDate())
        ) {
            throw new InvalidDateException();
        }
        return electionMapper.toDTO(electionRepository.save(electionMapper.toEntity(electionDTO)));
    }

    @Override
    public void addCandidateToElection(AddCandidateGroupRequestDTO candidateGroupDTO) {
        if (candidateGroupRepository.findByElectionIdAndCandidateId(candidateGroupDTO.getElectionId(), candidateGroupDTO.getCandidateId()).isPresent()) {
            throw new CandidateGroupDuplicateException();
        }
        candidateGroupDTO.setVoteCount(0);
        candidateGroupRepository.save(new CandidateGroup()
                .setElection(electionRepository.findById(candidateGroupDTO.getElectionId())
                        .orElseThrow(ElectionNotFoundException::new))
                .setCandidate(candidateRepository.findById(candidateGroupDTO.getCandidateId())
                        .orElseThrow(CandidateNotFoundException::new)));
    }

    @Override
    public List<CandidateDTO> getCandidatesForStudent(Integer electionId) {
        Election election = electionRepository.findById(electionId).orElseThrow(ElectionNotFoundException::new);
        if (election.getEndDate().before(new Date()) || election.getStartDate().after(new Date())) {
            throw new ElectionIsNotActiveException();
        }
        return candidateGroupRepository.findAllByElectionId(election.getId()).stream()
                .map(candidateGroup -> candidateMapper.toDTO(candidateGroup.getCandidate())).toList();
    }

    @Override
    public List<ElectionDTO> getInProgressElections() {
        return electionRepository.findAll().stream().map(election -> electionMapper.toDTO(election))
                .filter(electionDTO -> !electionDTO.getEndDate().before(new Date()) && !electionDTO.getStartDate().after(new Date()))
                .toList();
    }

    @Override
    public List<ElectionDTO> getElectionsHistoryForStudent() {
        Student student = studentRepository.findByStudentNumber(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .orElseThrow(StudentNotFoundException::new);
        return voteRepository.findAllByStudentId(student.getId())
                .stream().map(vote -> electionRepository.findById(vote.getElectionId()).orElseThrow(ElectionNotFoundException::new))
                .map(electionMapper::toDTO).toList();
    }
}
