package com.skillmap.backend.controller;

import com.skillmap.backend.dto.QuestionResponse;
import com.skillmap.backend.dto.QuizResultResponse;
import com.skillmap.backend.dto.QuizSubmitRequest;
import com.skillmap.backend.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/{weekNo}")
    public ResponseEntity<List<QuestionResponse>> getQuestionsForWeek(@PathVariable Integer weekNo) {
        return ResponseEntity.ok(quizService.getQuestionsForWeek(weekNo));
    }

    @PostMapping("/submit")
    public ResponseEntity<QuizResultResponse> submitQuiz(@RequestBody QuizSubmitRequest request) {
        return ResponseEntity.ok(quizService.submitQuiz(request));
    }


}
