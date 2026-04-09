package com.skillmap.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResultResponse {
    private int score;
    private int totalQuestions;
    
    // Maps Question ID to the correct answer so the frontend can display it
    private Map<Long, String> correctAnswers;
}
