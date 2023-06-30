package com.example.spring_study.Entity;

import com.example.spring_study.Enum.EmotionEnum;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 어노테이션 위치 수정
public class Diary extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    @Column(length = 200)
    private String subject; // 제목

    @NotNull
    private String img;

    @Column(columnDefinition = "TEXT", nullable = false, length = 32767)
    private String content; // 내용

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private EmotionEnum emotion; // 기분

    @JoinColumn(name="user_id")
    @ManyToOne
    private User user;

    public void update(String subject, String content, EmotionEnum emotion, String img) {
        this.subject = subject;
        this.content = content;
        this.emotion = emotion;
        this.img = img;
    }
}