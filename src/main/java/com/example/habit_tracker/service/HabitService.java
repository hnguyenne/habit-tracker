package com.example.habit_tracker.service;

import com.example.habit_tracker.model.Habit;
import com.example.habit_tracker.repository.HabitRepository;
import com.example.habit_tracker.repository.UserRepository;
import com.example.habit_tracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitService {
    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private UserRepository userRepository;

    // Get all habits of a user
    public List<Habit> getHabitsByUser(Long userId) {
        return habitRepository.findByUserId(userId);
    }

    // Create new habit
    public Habit createHabit(Long userId, Habit habit) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            habit.setUser(user.get());
            return habitRepository.save(habit);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Get habit by ID
    public Optional<Habit> getHabitById(Long id) {
        return habitRepository.findById(id);
    }

    // Update an existing habit
    public Habit updateHabit(Long id, Habit newHabitData) {
        // Find the habit by ID
        Optional<Habit> existingHabit = habitRepository.findById(id);

        // If the habit exists, update its fields and save it
        if (existingHabit.isPresent()) {
            Habit habit = existingHabit.get();
            habit.setName(newHabitData.getName());
            habit.setDescription(newHabitData.getDescription());
            habit.setFrequency(newHabitData.getFrequency());
            habit.setStreak(newHabitData.getStreak());
            return habitRepository.save(habit);
        } else {
            // If the habit is not found, throw an exception
            throw new RuntimeException("Habit not found");
        }
    }

    // Delete habit
    public void deleteHabit(Long id) {
        habitRepository.deleteById(id);
    }
}
