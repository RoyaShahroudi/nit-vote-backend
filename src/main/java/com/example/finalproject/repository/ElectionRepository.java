package com.example.finalproject.repository;

import com.example.finalproject.domain.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Integer> {
    List<Election> findAll();
}
