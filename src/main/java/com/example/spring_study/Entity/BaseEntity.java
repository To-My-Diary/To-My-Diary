package com.example.spring_study.Entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass // 테이블로 생성되지 않음
@EntityListeners(value = {AuditingEntityListener.class }) // JPA 내부에서 엔티티 객체의 생성을 감지
@Getter
public class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDate createDate; // 생성 날짜
}
