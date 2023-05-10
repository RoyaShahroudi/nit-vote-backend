package com.example.finalproject.repository;

import com.example.finalproject.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    Optional<Vote> findByStudentId(Integer studentId);

    Optional<Vote> findByStudentIdAndElectionId(Integer studentId, Integer electionId);
}
