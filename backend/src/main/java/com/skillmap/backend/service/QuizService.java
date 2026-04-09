package com.skillmap.backend.service;

import com.skillmap.backend.dto.QuestionResponse;
import com.skillmap.backend.dto.QuizResultResponse;
import com.skillmap.backend.dto.QuizSubmitRequest;
import com.skillmap.backend.entity.Question;
import com.skillmap.backend.entity.Quiz;
import com.skillmap.backend.entity.Roadmap;
import com.skillmap.backend.entity.User;
import com.skillmap.backend.repository.QuestionRepository;
import com.skillmap.backend.repository.QuizRepository;
import com.skillmap.backend.repository.RoadmapRepository;
import com.skillmap.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final RoadmapRepository roadmapRepository;

    private Long getCurrentUserLatestRoadmapId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Roadmap roadmap = roadmapRepository.findTopByUserIdOrderByIdDesc(user.getId())
                .orElseThrow(() -> new RuntimeException("Roadmap not found for user"));
        return roadmap.getId();
    }

    public void generateQuizzesForRoadmap(Roadmap roadmap) {
        for (int week = 1; week <= 6; week++) {
            Quiz quiz = new Quiz();
            quiz.setWeekNo(week);
            quiz.setRoadmapId(roadmap.getId());
            quiz = quizRepository.save(quiz);

            for (int q = 1; q <= 3; q++) {
                Question question = new Question();
                question.setQuizId(quiz.getId());
                question.setQuestion("Week " + week + " Question " + q + " about " + roadmap.getGoal() + "?");
                question.setCorrectAnswer("Answer " + q);
                questionRepository.save(question);
            }
        }
    }

    public List<QuestionResponse> getQuestionsForWeek(Integer weekNo) {
        Long roadmapId = getCurrentUserLatestRoadmapId();
        Quiz quiz = quizRepository.findByRoadmapIdAndWeekNo(roadmapId, weekNo)
                .orElseThrow(() -> new RuntimeException("Quiz not found for week " + weekNo));

        List<Question> questions = questionRepository.findByQuizId(quiz.getId());
        
        // Return questions without the the correctAnswer field
        return questions.stream()
                .map(q -> new QuestionResponse(q.getId(), q.getQuestion()))
                .collect(Collectors.toList());
    }

    public QuizResultResponse submitQuiz(QuizSubmitRequest request) {
        Long roadmapId = getCurrentUserLatestRoadmapId();
        Quiz quiz = quizRepository.findByRoadmapIdAndWeekNo(roadmapId, request.getWeekNo())
                .orElseThrow(() -> new RuntimeException("Quiz not found for week " + request.getWeekNo()));

        List<Question> questions = questionRepository.findByQuizId(quiz.getId());
        
        int score = 0;
        Map<Long, String> correctAnswers = new HashMap<>();

        for (Question q : questions) {
            String correctAnswer = q.getCorrectAnswer();
            correctAnswers.put(q.getId(), correctAnswer);

            // Compare user answer with correct answer (case-insensitive and trimmed for simplicity)
            String userAnswer = request.getAnswers().get(q.getId());
            if (userAnswer != null && userAnswer.trim().equalsIgnoreCase(correctAnswer.trim())) {
                score++;
            }
        }

        return new QuizResultResponse(score, questions.size(), correctAnswers);
    }
}
