package com.example.finalproject.repository;

import com.example.finalproject.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    List<Vote> findAllByStudentId(Integer studentId);

    Optional<Vote> findByStudentIdAndElectionId(Integer studentId, Integer electionId);

    List<Vote> findAll();
}
