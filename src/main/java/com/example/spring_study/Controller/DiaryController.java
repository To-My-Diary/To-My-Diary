package com.example.spring_study.Controller;

import com.example.spring_study.Dto.DiaryDto;
import com.example.spring_study.Entity.Diary;
import com.example.spring_study.Service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final DiaryService diaryService;


    @GetMapping("/show/diary")
    public Diary searchById(@PathVariable Long id) {
        return diaryService.searchById(id);
    }


    //전체 조회(목록)
    @GetMapping("/wholeview/diary")
    public List<Diary> searchAllDesc() {
        return diaryService.searchAllDesc();
    }

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

