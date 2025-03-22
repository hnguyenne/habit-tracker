package com.example.habit_tracker.service;

import com.example.habit_tracker.model.Habit;
import com.example.habit_tracker.model.HabitLog;
import com.example.habit_tracker.repository.HabitLogRepository;
import com.example.habit_tracker.repository.HabitRepository;
import com.example.habit_tracker.repository.UserRepository;
import com.example.habit_tracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;


@Service
public class HabitService {
    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HabitLogRepository habitLogRepository;

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

    public Habit completeHabit(Long habitId) {
        Optional<Habit> habOptional = habitRepository.findById(habitId);

        if(habOptional.isPresent()) {
            Habit habit = habOptional.get();
            User user = habit.getUser();

            // Prevent multiple completions in one day
            LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
            LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);

            boolean alreadyCompleted = habitLogRepository.existsByHabitIdAndCompletedAtBetween(habitId, startOfDay, endOfDay);
            if (alreadyCompleted) {
                throw new RuntimeException("Habit already completed today!");
            }

            HabitLog habitLog = new HabitLog(habit);
            habitLogRepository.save(habitLog);

            habit.setStreak(habit.getStreak() + 1);

            user.addPoints(10);

            userRepository.save(user);
            return habitRepository.save(habit);
        } else {
            throw new RuntimeException("Habit not found");
        }
    }

    public Map<String, Object> getHabitProgress(Long habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found"));

        int streak = habit.getStreak();
        int completionGoal = habit.getFrequency(); // Times per week

         // Get the start and end of the current week
        LocalDateTime startOfWeek = LocalDateTime.now().with(java.time.DayOfWeek.MONDAY).toLocalDate().atStartOfDay();
        LocalDateTime endOfWeek = startOfWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);

        int completedCount = habitLogRepository.findByHabitIdAndCompletedAtBetween(habitId, startOfWeek, endOfWeek).size();

        double progressPercentage = ((double) completedCount / completionGoal) * 100;

        Map<String, Object> progress = new HashMap<>();
        progress.put("habitId", habit.getId());
        progress.put("name", habit.getName());
        progress.put("streak", streak);
        progress.put("progress", progressPercentage + "%");

        return progress;
    }

    // Delete habit
    public void deleteHabit(Long habitId) {
        habitRepository.deleteById(habitId);
    }

    public List<HabitLog> geHabitLogs(Long habitId) {
        return habitLogRepository.findByHabitId(habitId);
    } 
}
