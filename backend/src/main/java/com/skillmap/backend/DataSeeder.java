package com.skillmap.backend;

import com.skillmap.backend.entity.Question;
import com.skillmap.backend.entity.Quiz;
import com.skillmap.backend.entity.Roadmap;
import com.skillmap.backend.entity.User;
import com.skillmap.backend.repository.QuestionRepository;
import com.skillmap.backend.repository.QuizRepository;
import com.skillmap.backend.repository.RoadmapRepository;
import com.skillmap.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoadmapRepository roadmapRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            System.out.println("Seeding initial data...");

            // 1. Create a dummy user
            User dummyUser = User.builder()
                    .email("test@example.com")
                    .password(passwordEncoder.encode("password"))
                    .xp(0)
                    .streak(0)
                    .build();
            dummyUser = userRepository.save(dummyUser);

            // 2. Create a dummy roadmap
            Roadmap dummyRoadmap = new Roadmap();
            dummyRoadmap.setUserId(dummyUser.getId());
            dummyRoadmap.setGoal("Full-Stack Web Development");
            dummyRoadmap.setContent("[]"); // Mock content for seed
            dummyRoadmap = roadmapRepository.save(dummyRoadmap);

            // 3. Create quizzes for week 1-4 and add 3 questions per week
            for (int week = 1; week <= 4; week++) {
                Quiz quiz = new Quiz();
                quiz.setWeekNo(week);
                quiz.setRoadmapId(dummyRoadmap.getId());
                quiz = quizRepository.save(quiz);

                for (int q = 1; q <= 3; q++) {
                    Question question = new Question();
                    question.setQuizId(quiz.getId());
                    question.setQuestion("Dummy Question " + q + " for Week " + week + "?");
                    question.setCorrectAnswer("Answer " + q);
                    questionRepository.save(question);
                }
            }
            
            System.out.println("Dummy seed data successfully added: User, Roadmap, and Quizzes (Weeks 1-4 with 3 questions each).");
        }
    }
}
