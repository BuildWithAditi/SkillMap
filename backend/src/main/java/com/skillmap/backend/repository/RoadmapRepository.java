package com.skillmap.backend.repository;
import java.util.Optional;
import com.skillmap.backend.entity.Roadmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {
    List<Roadmap> findByUserId(Long userId);
    Optional<Roadmap> findTopByUserIdOrderByIdDesc(Long userId);
}
