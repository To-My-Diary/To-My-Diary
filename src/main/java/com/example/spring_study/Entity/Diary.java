package com.example.spring_study.Entity;

import com.example.spring_study.Enum.EmotionEnum;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 어노테이션 위치 수정
@Data
public class Diary extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    @Column(length = 200)
    private String subject; // 제목

    //@NotNull
    //private String img; // 이미지
    //@ManyToOne(targetEntity= Image.class,fetch=FetchType.EAGER)
    //@OneToMany
    //@JoinTable(name="Image", //매핑할 조인 테이블 이름
      //      joinColumns = @JoinColumn(name="img"),//현재 엔터티를 참조하는 외래 키
        //    inverseJoinColumns = @JoinColumn(name="id")) //반대 엔터티의 외래키
    //private List<Image> img=new ArrayList<Image>(); //이미지

    @Column(columnDefinition = "TEXT", nullable = false, length = 32767)
    private String content; // 내용

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private EmotionEnum emotion; // 기분

    @JoinColumn(name="user_id")
    @ManyToOne
    private User user;

    public void update(String subject, String content, EmotionEnum emotion) {
        this.subject = subject;
        this.content = content;
        this.emotion = emotion;
    }
}