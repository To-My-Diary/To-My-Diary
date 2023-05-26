package com.example.spring_study.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    private LocalDateTime createDate;
    @ColumnDefault("false")
    private boolean achieve;
    private LocalDateTime planDate;
    @ColumnDefault("0")
    private int achieve_rate;
    @ManyToOne
    @Setter
    private User user;
    @Builder
    Goal(String content, boolean achieve, int achieve_rate){
        this.content = content;
        this.achieve = achieve;
        this.achieve_rate = achieve_rate;
        this.createDate = LocalDateTime.now();
    }
}
