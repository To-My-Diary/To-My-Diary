package com.example.spring_study.Repository;

import com.example.spring_study.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<Image, Long> {
    Optional<Image> findAllById(Long diaryId);

    List<Image> findAllByDiary_DiaryId(Long diaryId);
}
