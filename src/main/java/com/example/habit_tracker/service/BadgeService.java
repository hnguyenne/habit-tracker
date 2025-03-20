package com.example.habit_tracker.service;
import com.example.habit_tracker.model.User;

public class BadgeService {
    public String getUserBadge(User user) {
        int points = user.getPoints();
    
        if (points >= 500) return "Gold Badge";
        if (points >= 200) return "Silver Badge";
        if (points >= 100) return "Bronze Badge";
        
        return "No Badge Yet";
    }
}
