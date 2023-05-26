package com.example.spring_study.Entity;

import com.example.spring_study.Enum.GenderType;
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
    @Column(length=50, nullable = false)
    private String email;
    @Column(nullable = false)
    private String pw;
    @Column(nullable = false)
    private String pw_val;
    @Column(nullable = false)
    private String name;
    private int tel;
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Goal> goalList = new ArrayList<>();
    public void addGoalList(Goal goal){
        goal.setUser(this);
        this.goalList.add(goal);
    }
    @Builder
    public User(String email, String pw, String pw_val, String name, GenderType gender, int tel){
        this.email = email;
        this.pw = pw;
        this.pw_val = pw_val;
        this.name = name;
        this.gender = gender;
        this.tel = tel;
    }
}
