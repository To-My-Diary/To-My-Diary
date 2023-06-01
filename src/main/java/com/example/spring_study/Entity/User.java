package com.example.spring_study.Entity;

import com.example.spring_study.Enum.GenderEnum;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder // 빌더 어노테이션 위치 수정
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String email;
    @Column(nullable = false)
    private String pw;
    @Column(length=20)
    private String name;
    private int tel;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Goal> goalList = new ArrayList<>();
    public void addGoalList(Goal goal){
        goal.setUser(this);
        this.goalList.add(goal);
    }
}
