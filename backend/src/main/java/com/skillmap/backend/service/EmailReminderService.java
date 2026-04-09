package com.skillmap.backend.service;

import com.skillmap.backend.entity.User;
import com.skillmap.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailReminderService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    public void sendWeeklyReminder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new RuntimeException("User email is missing");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Learning Reminder: Weekly Tasks");
        message.setText("Complete your weekly learning tasks");

        mailSender.send(message);
    }
}
