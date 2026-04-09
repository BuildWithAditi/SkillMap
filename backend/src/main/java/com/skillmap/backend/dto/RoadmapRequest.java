package com.skillmap.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class RoadmapRequest {
    private Long userId;
    private List<String> skills;
    private String goal;
}
