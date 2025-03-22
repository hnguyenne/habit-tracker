package com.example.habit_tracker.repository;

import com.example.habit_tracker.model.HabitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HabitLogRepository extends JpaRepository<HabitLog, Long> {
    List<HabitLog> findByHabitId(Long habitId);
    boolean existsByHabitIdAndCompletedAtBetween(Long habitId, LocalDateTime start, LocalDateTime end);
    List<HabitLog> findByHabitIdAndCompletedAtBetween(Long habitId, LocalDateTime start, LocalDateTime end);
}
