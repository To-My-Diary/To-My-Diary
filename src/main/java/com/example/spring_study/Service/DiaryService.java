package com.example.spring_study.Service;

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

