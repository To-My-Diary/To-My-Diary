package com.example.spring_study.Entity;

import com.example.spring_study.Enum.AchieveEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DetailGoal extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detailGoalId; // 일정 상세 번호 (기본키)

    @Column(nullable = false)
    private String content; // 내용

    @Enumerated(EnumType.STRING)
    private AchieveEnum achieve = AchieveEnum.doing; // 달성 여부

    @Column(nullable = false)
    private LocalDate planDate; // 목표 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="goal_id")
    private Goal goal;
}
