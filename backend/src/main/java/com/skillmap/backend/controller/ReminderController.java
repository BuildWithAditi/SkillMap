package com.skillmap.backend.controller;

import com.skillmap.backend.service.EmailReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reminders")
@RequiredArgsConstructor
public class ReminderController {

    private final EmailReminderService emailReminderService;

    @PostMapping("/send/{userId}")
    public ResponseEntity<String> sendReminder(@PathVariable Long userId) {
        emailReminderService.sendWeeklyReminder(userId);
        return ResponseEntity.ok("Reminder sent to user successfully");
    }
}
