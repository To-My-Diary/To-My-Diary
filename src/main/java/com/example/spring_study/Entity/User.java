package com.example.spring_study.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length=20)
    private String name;
    @Column(name = "age")
    private int age;
    @Builder
    public User(String name, int age){
        this.name = name;
        this.age=age;
    }
}
