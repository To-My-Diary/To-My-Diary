package com.example.spring_study;

import com.example.spring_study.Entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

interface GoalRepository extends JpaRepository<Goal, Long> {
}
