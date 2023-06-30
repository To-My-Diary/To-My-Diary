package com.example.spring_study.Controller;

<<<<<<< HEAD
import com.example.spring_study.Entity.Diary;
import com.example.spring_study.Service.DiaryService;
import groovy.util.logging.Log4j;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController //해당 클래스는 REST API 처리하는 Controller (@Controller + @ResponseBody)
@CrossOrigin // CORS이슈 방지
@AllArgsConstructor
@RequestMapping("/") // base url 설정, url을 컨트롤러의 메서드와 매핑할 때 사용
@Log
public class DiaryController {

    private final DiaryService service;
    @PostMapping
    //ResponseEntity는 HttpEntity 클래스를 상속받아 구현한 클래스이며 HttpStatus, HttpHeaders, HttpBody를 포함
    public ResponseEntity<Diary> create(@RequestBody Diary request){

    }
    @GetMapping("{id}")
    //일기 하나씩 조회 하는 api
    //GetMapping에 경로로받은 id값을 쓰기위해서 @PathVariable
    public ResponseEntity<Diary> readOne(@PathVariable Long id){

    }

    @GetMapping
    //일기 전체를 조회하는 api
    public ResponseEntity<List<DiaryResponse>> readAll(){

    }

}

=======
import com.example.spring_study.Dto.DiaryDto;
import com.example.spring_study.Service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final DiaryService diaryService;

    /** 일기 저장 */
    @PostMapping("/save/diary")
    public void saveDiary(@RequestBody DiaryDto diaryDto) {
        diaryService.saveDiary(diaryDto);
    }

    /** 일기 수정 */
    @PostMapping("/modify/diary")
    public void modifyDiary(@RequestBody DiaryDto diaryDto) {
        diaryService.modifyDiary(diaryDto);
    }

    /** 일기 삭제 */
    @PostMapping("/delete/diary/{diaryId}")
    public void deleteDiary(@PathVariable("diaryId") Long diaryId) {
        diaryService.deleteDiary(diaryId);
    }

}
>>>>>>> 0a81bde3af1f8cfa49573f73f155e61cf4b7cc00
