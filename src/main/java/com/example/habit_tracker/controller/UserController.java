package com.example.habit_tracker.controller;

import com.example.habit_tracker.service.BadgeService;
import com.example.habit_tracker.service.UserService;
import com.example.habit_tracker.model.User;
import com.example.habit_tracker.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        
        userRepository.save(user);
        return ResponseEntity.ok("User created successfully!");
    }

    @GetMapping("/{userId}/badge")
    public String getUserBadge(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            BadgeService badgeService = new BadgeService();
            return badgeService.getUserBadge(userOptional.get());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<User>> getLeaderboard(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(userService.getLeaderboard(limit));
    }
}
