package com.example.spring_study.Controller;

import com.example.spring_study.DTO.DiaryDto;
import com.example.spring_study.Entity.Diary;
import com.example.spring_study.Entity.Image;
import com.example.spring_study.Repository.FileRepository;
import com.example.spring_study.Service.DiaryService;
import com.example.spring_study.Service.FileService;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import static com.example.spring_study.Service.FileService.decompressBytes;

@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final DiaryService diaryService;
    private final FileService fileService;
    private final FileRepository fileRepository;

    @GetMapping("/show/diary/{id}")
    public Diary searchById(@PathVariable Long id) {
        return diaryService.searchById(id);
    }

    //전체 조회(목록)
    @GetMapping("/wholeview/diary")
    public List<Diary> searchAllDesc(Principal principal) {
        return diaryService.searchAllDesc(principal.getName());
    }

    /** 일기 저장 */
    @PostMapping("/save/diary")
    public void saveDiary(@RequestBody DiaryDto diaryDto, Principal principal) {
        diaryService.saveDiary(diaryDto, principal.getName());
    }

    /** 일기 수정 */
    @PostMapping("/modify/diary")
    public void modifyDiary(@RequestBody DiaryDto diaryDto, Principal principal) {
        diaryService.modifyDiary(diaryDto, principal.getName());
    }

    /** 일기 삭제 */
    @PostMapping("/delete/diary/{diaryId}")
    public void deleteDiary(@PathVariable("diaryId") Long diaryId) {
        diaryService.deleteDiary(diaryId);
    }

    /** 파일 업로드 */
    @PostMapping("/upload")
    public ResponseEntity<String> saveFile(@RequestParam(name="file") List<MultipartFile> files, @RequestParam(name = "diaryId") Long diaryId) throws IOException {
        try {
            fileService.save(files, diaryId); // db에 저장
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) { // 에러 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

    /** 파일 띄우기 */
    @GetMapping("/image/{diaryId}")
    public ResponseEntity<List<byte[]>> getFile(@PathVariable Long diaryId, HttpServletResponse response) {
        List<Image> images = fileRepository.findAllByDiary_DiaryId(diaryId);
        try{
            images.stream().forEach(e -> e.setPicByte(decompressBytes(e.getPicByte())));
//        image.get(0).setPicByte(decompressBytes(image.get(0).getPicByte())); // 이미지 압축 풀기
            return ResponseEntity.ok().body(images.stream().map(e -> e.getPicByte()).collect(Collectors.toList()));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


}

