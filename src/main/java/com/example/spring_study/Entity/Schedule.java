package com.example.spring_study.Entity;

import com.example.spring_study.Enum.AchieveEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data // Getter & Setter 메서드
@Builder // 엔티티 생성 & 초기화
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId; // 일정 번호 (기본키)

    @Column(nullable = false)
    private String msg; // 내용

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private AchieveEnum achieve; // 달성 여부

    private LocalDateTime planDate; // 목표 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user; // 사용자 (작성자)

    public void update(String msg, AchieveEnum achieve) {
        this.msg = msg;
        this.achieve = achieve;
    }

}
