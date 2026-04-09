package com.skillmap.backend.repository;

import com.skillmap.backend.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findByRoadmapIdAndWeekNo(Long roadmapId, Integer weekNo);
}
