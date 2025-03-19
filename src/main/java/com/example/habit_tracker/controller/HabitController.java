package com.example.habit_tracker.controller;

import com.example.habit_tracker.model.Habit;
import com.example.habit_tracker.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/habits")
public class HabitController {
    @Autowired
    private HabitService habitService;

    // Get all habits for a user
    @GetMapping("/user/{userId}")
    public List<Habit> getHabitsByUser(@PathVariable Long userId) {
        return habitService.getHabitsByUser(userId);
    }

    // Create a new habit
    @PostMapping("/user/{userId}")
    public Habit createHabit(@PathVariable Long userId, @RequestBody Habit habit) {
        return habitService.createHabit(userId, habit);
    }

    // Get a single habit
    @GetMapping("/{id}")
    public Optional<Habit> getHabitById(@PathVariable Long id) {
        return habitService.getHabitById(id);
    }

    // Update a habit
    @PutMapping("/{id}")
    public Habit updateHabit(@PathVariable Long id, @RequestBody Habit habit) {
        return habitService.updateHabit(id, habit);
    }

    // Delete a habit
    @DeleteMapping("/{id}")
    public void deleteHabit(@PathVariable Long id) {
        habitService.deleteHabit(id);
    }
}
