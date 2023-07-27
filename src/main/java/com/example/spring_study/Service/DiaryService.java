package com.example.spring_study.Service;

import com.example.spring_study.DTO.DiaryDto;
import com.example.spring_study.Entity.Diary;
import com.example.spring_study.Repository.DiaryRepository;
import com.example.spring_study.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    /** 일기 저장 */
    public void saveDiary(DiaryDto diaryDto) {
        diaryRepository.save(dtoToEntity(diaryDto));
        System.out.println("[" + diaryDto.getSubject() + "] 일기가 저장됐습니다.");
    }

    /** 일기 수정 */
    @Transactional
    public void modifyDiary(DiaryDto diaryDto) {
        Diary diary = diaryRepository.findByDiaryId(diaryDto.getDiaryId());
        diary.update(diaryDto.getSubject(), diaryDto.getContent(), diaryDto.getEmotion(), diaryDto.getImg());
        System.out.println("[" + diaryDto.getSubject() + "] 일기가 수정됐습니다.");
    }

    /** 일기 삭제 */
    public void deleteDiary(Long diaryId) {
        diaryRepository.deleteById(diaryId);
        System.out.println(diaryId + " 번 일기가 삭제됐습니다.");
    }

    /** Dto -> Entity */
    Diary dtoToEntity(DiaryDto dto) {
        Diary diary = Diary.builder()
//                .diaryId(dto.getDiaryId())
                .subject(dto.getSubject())
                .content(dto.getContent())
                .user(userRepository.findByEmail(dto.getUserId()).get())
                .emotion(dto.getEmotion())
                .img(dto.getImg())
                .build();
        return diary;
    }

}
