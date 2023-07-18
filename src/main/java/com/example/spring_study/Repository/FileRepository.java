package com.example.spring_study.Repository;

import com.example.spring_study.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Image, Long> {

}
