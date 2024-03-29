package com.example.spring_study.Service;

import com.example.spring_study.DTO.DiaryDto;
import com.example.spring_study.Entity.Diary;
import com.example.spring_study.Entity.User;
import com.example.spring_study.Repository.DiaryRepository;
import com.example.spring_study.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;


    public Diary searchById(Long id){ //데이터 검색
        return diaryRepository.findByDiaryId(id);
        //return DiaryRepository.findByDiaryId(id).orElseThrow(ExceptionBoard.NOT_FOUND_POST::getException).counting();
    }

    public List<Diary> searchAllDesc(String userId) { //모든 데이터 검색

        List<Diary> allByUserEmail = diaryRepository.findAllByUser_Email(userId);

        allByUserEmail.forEach(i -> {
            System.out.printf(i.getContent());
        });


        return allByUserEmail;
    }

    /** 일기 저장 */
    public void saveDiary(DiaryDto diaryDto, String name) {
        User user = userRepository.findByEmail(name).get();
        diaryDto.setUserId(user.getEmail());
        try {
            diaryRepository.save(dtoToEntity(diaryDto));
            System.out.println("[" + diaryDto.getSubject() + "] 일기가 저장됐습니다.");
        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /** 일기 수정 */
    @Transactional
    public void modifyDiary(DiaryDto diaryDto, String name) {
        User user = userRepository.findByEmail(name).get();
        diaryDto.setUserId(user.getEmail());

        Diary diary = diaryRepository.findByDiaryId(diaryDto.getDiaryId());
        diary.update(diaryDto.getSubject(), diaryDto.getContent(), diaryDto.getEmotion());
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
                //.img(dto.getImg())
                .build();
        return diary;
    }

}