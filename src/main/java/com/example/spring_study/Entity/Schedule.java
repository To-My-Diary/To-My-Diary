package com.example.spring_study.Entity;

import com.example.spring_study.Enum.AchieveEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Setter
    private AchieveEnum achieve; // 달성 여부

    private LocalDate planDate; // 목표 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    @JsonIgnore  //서로를 참조하면서 무한 재귀함수가 실행 되게 된다. 해결방법 JsonIgnore
    private User user; // 사용자 (작성자)

    public void update(String msg, AchieveEnum achieve, LocalDate planDate) {
        this.msg = msg;
        this.achieve = achieve;
        this.planDate = planDate;
    }

}
