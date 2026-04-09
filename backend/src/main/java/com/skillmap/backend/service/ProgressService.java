package com.skillmap.backend.service;

import com.skillmap.backend.dto.ProgressRequest;
import com.skillmap.backend.dto.ProgressResponse;
import com.skillmap.backend.entity.Progress;
import com.skillmap.backend.entity.User;
import com.skillmap.backend.repository.ProgressRepository;
import com.skillmap.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;

    @Transactional
    public ProgressResponse completeWeek(ProgressRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getUserId()));

        List<Progress> existingProgress = progressRepository.findByUserIdAndWeekNo(request.getUserId(), request.getWeekNo());
        
        // Prevent duplicate processing if already completed
        if (!existingProgress.isEmpty() && Boolean.TRUE.equals(existingProgress.get(0).getCompleted())) {
            return new ProgressResponse(user.getXp(), user.getStreak());
        }

        int xpEarned = 10;
        int newStreak;

        // Calculate Gamification Streak based on consecutive week mapping
        if (request.getWeekNo() > 1) {
            List<Progress> previousWeekProgress = progressRepository.findByUserIdAndWeekNo(request.getUserId(), request.getWeekNo() - 1);
            if (!previousWeekProgress.isEmpty() && Boolean.TRUE.equals(previousWeekProgress.get(0).getCompleted())) {
                newStreak = user.getStreak() + 1; // Consecutive completion
            } else {
                newStreak = 1; // Skipped the previous week, resetting streak
            }
        } else {
            // For week 1, if prior streak was 0 we start at 1, else we just increment if they continuously started new programs
            newStreak = (user.getStreak() == 0) ? 1 : user.getStreak() + 1; 
        }

        // Update User gamification points
        user.setXp(user.getXp() + xpEarned);
        user.setStreak(newStreak);
        userRepository.save(user);

        // Update or insert Progress entity
        Progress progress = existingProgress.isEmpty() ? new Progress() : existingProgress.get(0);
        progress.setUserId(user.getId());
        progress.setWeekNo(request.getWeekNo());
        progress.setCompleted(true);
        progress.setXpEarned(xpEarned);
        progressRepository.save(progress);

        return new ProgressResponse(user.getXp(), user.getStreak());
    }
}
