package com.example.spring_study.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length=20)
    private String name;
    @Column(name = "age")
    private int age;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Goal> goalList = new ArrayList<>();
    public void addGoalList(Goal goal){
        this.goalList.add(goal);
    }
    @Builder
    public User(String name, int age){
        this.name = name;
        this.age=age;
    }
}
