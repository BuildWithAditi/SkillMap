package com.skillmap.backend.dto;

import lombok.Data;
import java.util.Map;

@Data
public class QuizSubmitRequest {
    private Integer weekNo;
    
    // Maps Question ID to user's answer
    private Map<Long, String> answers;
}
