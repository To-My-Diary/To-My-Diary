package com.example.spring_study.Controller;

import com.example.spring_study.DTO.DiaryDto;
import com.example.spring_study.Entity.Diary;
import com.example.spring_study.Entity.Image;
import com.example.spring_study.Service.DiaryService;
import com.example.spring_study.Service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.spring_study.Service.FileService.decompressBytes;

@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final DiaryService diaryService;
    private final FileService fileService;

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

    /** 파일 업로드 */
    @PostMapping("/upload")
    public ResponseEntity<String> saveFile(@RequestParam(name="file") MultipartFile file) throws IOException {
        try {
            Image savedImage = fileService.save(file); // db에 저장
            return ResponseEntity.ok("Image uploaded successfully. Image ID: " + savedImage.getId());
        } catch (IOException e) { // 에러 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

    /** 파일 띄우기 */
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id, HttpServletResponse response) {
        Image image = fileService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid image ID: " + id));
        image.setPicByte(decompressBytes(image.getPicByte())); // 이미지 압축 풀기
        return ResponseEntity.ok().contentType(MediaType.valueOf(image.getType())).body(image.getPicByte());
    }

}
