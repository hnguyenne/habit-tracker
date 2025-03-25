package com.example.habit_tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.habit_tracker.model.User;
import com.example.habit_tracker.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> getLeaderboard(int limit) {
        return userRepository.findTopUsers(PageRequest.of(0, limit));
    }

}
