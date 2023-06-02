package com.example.spring_study.Entity;

<<<<<<< Updated upstream
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
=======
import com.example.spring_study.Enum.AchieveEnum;
import lombok.*;
>>>>>>> Stashed changes
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
    @Builder
    Goal(String content, boolean achieve, int achieve_rate){
        this.content = content;
        this.achieve = achieve;
        this.achieve_rate = achieve_rate;
        this.createDate = LocalDateTime.now();
    }
}
