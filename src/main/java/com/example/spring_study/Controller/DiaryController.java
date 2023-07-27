package com.example.spring_study.Controller;

import com.example.spring_study.DTO.DiaryDto;
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
