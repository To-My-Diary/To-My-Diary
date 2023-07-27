package com.example.spring_study.Controller;

<<<<<<< HEAD

import com.example.spring_study.Service.DiaryService;
import com.example.spring_study.Dto.DiaryDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;




@NoArgsConstructor
@AllArgsConstructor
@Controller
public class DiaryController {

    private final DiaryService diaryService;

    //일기 하나씩 상세조회 하는 api

   // @PostMapping("/show/diary")
    //public void showDiary(@RequestBody DiaryDto diaryDto) {
      //  DiaryService.showDiary(diaryDto);
   // }

    @GetMapping("/show/diary")
    public DiaryDto searchById(@PathVariable Long id) {
        return DiaryService.searchById(id);
    }



    //일기 전체를 조회하는 api (목록 조회)
    //@PostMapping("/wholeview/diary")
    //public void wholeviewDiary(@RequestBody DiaryDto diaryDto) {
       // DiaryService.wholeviewDiary(diaryDto);
    //}

    //전체 조회(목록)
    @GetMapping("/wholeview/diary")
    public List<DiaryDto> searchAllDesc() {
        return DiaryService.searchAllDesc();
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
