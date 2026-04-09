package com.skillmap.backend.dto;

import lombok.Data;

@Data
public class ProgressRequest {
    private Long userId;
    private Integer weekNo;
}
