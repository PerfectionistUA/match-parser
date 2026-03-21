package com.example.match_parser.repository;

import com.example.match_parser.entity.DivisionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DivisionResultRepository extends JpaRepository<DivisionResult, Long> {

    List<DivisionResult> findByMatchId(Long matchId);
}