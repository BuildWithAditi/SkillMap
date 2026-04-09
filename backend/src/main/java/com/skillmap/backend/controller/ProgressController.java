package com.skillmap.backend.controller;

import com.skillmap.backend.dto.ProgressRequest;
import com.skillmap.backend.dto.ProgressResponse;
import com.skillmap.backend.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @PostMapping("/complete")
    public ResponseEntity<ProgressResponse> completeWeek(@RequestBody ProgressRequest request) {
        ProgressResponse response = progressService.completeWeek(request);
        return ResponseEntity.ok(response);
    }
}
