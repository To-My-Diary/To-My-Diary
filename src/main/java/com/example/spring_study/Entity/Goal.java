package com.example.spring_study.Entity;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long goal_id;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    private LocalDateTime createDate;
    @ColumnDefault("false")
    private boolean achieve;
    private LocalDateTime planDate;
    @ColumnDefault("0")
    private int achieve_rate;
    @ManyToOne
    private User user;
    @Builder
    Goal(String content, boolean achieve, int achieve_rate, User user){
        this.content = content;
        this.achieve = achieve;
        this.achieve_rate = achieve_rate;
        this.createDate = LocalDateTime.now();
        this.user = user;
    }
}
