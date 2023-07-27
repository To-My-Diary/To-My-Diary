package com.example.spring_study.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.spring_study.Enum.AchieveEnum;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 어노테이션 위치 수정
public class Goal extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long goalId; // 기본키

    @Column(nullable = false)
    private String content; // 목표

    @Enumerated(EnumType.STRING)
    private AchieveEnum achieve; // 달성 여부

    private LocalDate planDate; // 날짜

    private int achieveRate; // 달성률

    private String color; // 색깔

    @JoinColumn(name="user_id")
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "goal")
    private List<DetailGoal> detailGoals = new ArrayList<>(); // 상세 목표 리스트
}
