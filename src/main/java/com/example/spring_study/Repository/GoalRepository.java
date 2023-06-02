package com.example.spring_study.Repository;

import com.example.spring_study.Entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD

<<<<<<< Updated upstream
public interface GoalRepository extends JpaRepository<Goal,Long> {
=======
@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

>>>>>>> Stashed changes
=======
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
>>>>>>> 56a958fd51de5c5bd8df58d1041454306cc991d8
}
