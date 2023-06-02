package com.example.spring_study.Repository;

import com.example.spring_study.Entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< Updated upstream
public interface GoalRepository extends JpaRepository<Goal,Long> {
=======
@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

>>>>>>> Stashed changes
}
