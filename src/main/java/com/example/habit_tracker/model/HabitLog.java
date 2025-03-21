package com.example.habit_tracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "habit_log")
@Getter
@Setter
public class HabitLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "habit_id", nullable = false)
    private Habit habit;

    private LocalDateTime completedAt;

    public HabitLog() {
        this.completedAt = LocalDateTime.now();
    }

    public HabitLog(Habit habit) {
        this.habit = habit;
        this.completedAt = LocalDateTime.now();
    }
}
