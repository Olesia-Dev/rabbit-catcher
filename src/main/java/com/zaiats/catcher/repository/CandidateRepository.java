package com.zaiats.catcher.repository;

import com.zaiats.catcher.repository.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
}
