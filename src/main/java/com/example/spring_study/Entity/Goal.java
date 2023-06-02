package com.example.spring_study.Entity;

<<<<<<< HEAD
<<<<<<< Updated upstream
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
=======
import com.example.spring_study.Enum.AchieveEnum;
import lombok.*;
>>>>>>> Stashed changes
=======
import lombok.*;
>>>>>>> 56a958fd51de5c5bd8df58d1041454306cc991d8
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
<<<<<<< HEAD
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "TEXT", nullable = false)
=======
@AllArgsConstructor
@Builder // 빌더 어노테이션 위치 수정
public class Goal extends BaseEntity{    // 생성날짜 클래스 상속으로 대체
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long goalId;   // 소대문자, 언더바 양식 통일
    @Column(length = 32767, columnDefinition = "TEXT", nullable = false) // 글자 길이 수정
>>>>>>> 56a958fd51de5c5bd8df58d1041454306cc991d8
    private String content;
    @ColumnDefault("false")
<<<<<<< Updated upstream
    private boolean achieve;
=======
    @Enumerated(EnumType.STRING)
    private AchieveEnum achieve;
>>>>>>> Stashed changes
    private LocalDateTime planDate;
    @ColumnDefault("0")
    private int achieve_rate;
    @ManyToOne
    @Setter
    private User user;
<<<<<<< HEAD
    @Builder
    Goal(String content, boolean achieve, int achieve_rate){
        this.content = content;
        this.achieve = achieve;
        this.achieve_rate = achieve_rate;
        this.createDate = LocalDateTime.now();
    }
=======
>>>>>>> 56a958fd51de5c5bd8df58d1041454306cc991d8
}
