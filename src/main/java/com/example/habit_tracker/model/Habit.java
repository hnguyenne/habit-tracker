package com.example.habit_tracker.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "s")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private int frequency; 

    @Column(nullable = false)
    private int streak;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
