package com.example.spring_study.Service;

<<<<<<< HEAD
import com.example.spring_study.Entity.Diary;
import com.example.spring_study.Repository.DiaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class DiaryService {
    private final DiaryRepository DiaryRepository;

    public Diary add(DiaryRequest request){ // 데이터 저장
        Diary todoEntity = new Diary();
        todoEntity.diaryId(request.diaryId());
        todoEntity.achieve(request.achieve());

        return this.DiaryRepository.save(todoEntity); // 레파지토리에 데이터베이스 값 입력 save() 제네릭으로 받은 타입을 반환, 반환값은 todoentity
    }

    public Diary searchById(Long id){ //데이터 검색
        return this.DiaryRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Diary> searchAll() { //모든 데이터 검색
        return this.DiaryRepository.findAll();
    }

    //id를 받고 request로받은 값으로 변경
    public Diary updateById(Long id, DiaryRequest request){ //데이터 수정
        Diary todoEntity = this.searchById(id);
        if(request.diaryId() != null){
            todoEntity.diaryId(request.diaryId());
        }
        if(request.achieve() != null) {
            todoEntity.achieve(request.achieve());
        }
        return this.DiaryRepository.save(todoEntity);
    }

    public void deleteById(Long id){ //데이터 삭제
        this.DiaryRepository.deleteById(id);
    }

    public void deleteAll(){ //모든 데이터 삭제
        this.DiaryRepository.deleteAll();
    }
}

=======
import com.example.spring_study.Dto.DiaryDto;
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
>>>>>>> 0a81bde3af1f8cfa49573f73f155e61cf4b7cc00
