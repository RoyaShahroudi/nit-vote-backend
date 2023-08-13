package com.example.finalproject.repository;

import com.example.finalproject.domain.CandidateGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CandidateGroupRepository extends JpaRepository<CandidateGroup, Integer> {
    List<CandidateGroup> findAllByElectionId(int electionId);

    Optional<CandidateGroup> findByElectionIdAndCandidateId(int electionId, int candidateId);


}
