package com.skillmap.backend.service;

import com.skillmap.backend.dto.RoadmapRequest;
import com.skillmap.backend.entity.Roadmap;
import com.skillmap.backend.repository.RoadmapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoadmapService {

    private final RoadmapRepository roadmapRepository;
    private final QuizService quizService;

    public Roadmap generateRoadmap(RoadmapRequest request) {
        // Here we mock the AI API response to generate a 6-week roadmap
        // In a real scenario, an HTTP call to OpenAI/Claude would be made here with the user's skills and goal
        String roadmapContentJson = generateMockRoadmapJson(request.getGoal());

        Roadmap roadmap = new Roadmap();
        roadmap.setUserId(request.getUserId());
        roadmap.setGoal(request.getGoal());
        roadmap.setContent(roadmapContentJson);

        Roadmap savedRoadmap = roadmapRepository.save(roadmap);
        quizService.generateQuizzesForRoadmap(savedRoadmap);
        return savedRoadmap;
    }

    private String generateMockRoadmapJson(String goal) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");
        
        for (int i = 1; i <= 6; i++) {
            jsonBuilder.append("  {\n");
            jsonBuilder.append("    \"week\": ").append(i).append(",\n");
            
            // Generate some contextual mock data based on the goal
            String title = (i == 1) ? "Introduction to " + goal : 
                           (i == 6) ? "Mastery & Final Project for " + goal : 
                           "Deep Dive into " + goal + " part " + (i - 1);
                           
            jsonBuilder.append("    \"title\": \"").append(title).append("\",\n");
            jsonBuilder.append("    \"tasks\": [\n");
            jsonBuilder.append("      \"Study core concepts of week ").append(i).append("\",\n");
            jsonBuilder.append("      \"Complete hands-on exercise\"\n");
            jsonBuilder.append("    ],\n");
            jsonBuilder.append("    \"resources\": [\n");
            jsonBuilder.append("      \"Official Documentation\",\n");
            jsonBuilder.append("      \"Video Tutorial on ").append(goal).append(" (Part ").append(i).append(")\"\n");
            jsonBuilder.append("    ],\n");
            jsonBuilder.append("    \"quiz\": [\n");
            jsonBuilder.append("      \"What is the primary function learned in week ").append(i).append("?\"\n");
            jsonBuilder.append("    ]\n");
            jsonBuilder.append("  }");
            
            if (i < 6) {
                jsonBuilder.append(",");
            }
            jsonBuilder.append("\n");
        }
        
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }
}
