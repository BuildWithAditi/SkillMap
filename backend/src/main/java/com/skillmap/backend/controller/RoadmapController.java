package com.skillmap.backend.controller;

import com.skillmap.backend.dto.RoadmapRequest;
import com.skillmap.backend.entity.Roadmap;
import com.skillmap.backend.service.RoadmapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roadmap")
@RequiredArgsConstructor
public class RoadmapController {

    private final RoadmapService roadmapService;

    @PostMapping("/generate")
    public ResponseEntity<Roadmap> generateRoadmap(@RequestBody RoadmapRequest request) {
        Roadmap generatedRoadmap = roadmapService.generateRoadmap(request);
        return ResponseEntity.ok(generatedRoadmap);
    }
}
