package com.example.spring_study.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 어노테이션 위치 수정
public class Goal extends BaseEntity{    // 생성날짜 클래스 상속으로 대체
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long goalId;   // 소대문자, 언더바 양식 통일
    @Column(length = 32767, columnDefinition = "TEXT", nullable = false) // 글자 길이 수정
    private String content;
    @ColumnDefault("false")
    private boolean achieve;
    private LocalDateTime planDate;
    @ColumnDefault("0")
    private int achieve_rate;
    @ManyToOne
    private User user;
}
