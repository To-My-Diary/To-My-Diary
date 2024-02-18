package com.example.spring_study.Controller;

import static com.example.spring_study.Service.FileService.decompressBytes;

import com.example.spring_study.DTO.DiaryDto;
import com.example.spring_study.DTO.Response.ResponseDto;
import com.example.spring_study.DTO.Response.ResponseStatus;
import com.example.spring_study.Entity.Diary;
import com.example.spring_study.Entity.Image;
import com.example.spring_study.Repository.FileRepository;
import com.example.spring_study.Service.DiaryService;
import com.example.spring_study.Service.FileService;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DiaryController {

    private final DiaryService diaryService;
    private final FileService fileService;
    private final FileRepository fileRepository;

    @GetMapping("/show/diary/{id}")
    public Diary searchById(@PathVariable Long id) {
        return diaryService.searchById(id);
    }

    @GetMapping("/diary/{year}/{month}/{day}")
    public ResponseDto searchByDay(@PathVariable("year") int year, @PathVariable("month") int month,
                                   @PathVariable("day") int day,
                                   Principal principal) {
        return new ResponseDto(diaryService.searchByDay(LocalDate.of(year, month, day), principal.getName()));
    }

    //전체 조회(목록)
    @GetMapping("/wholeview/diary")
    public List<Diary> searchAllDesc(Principal principal) {
        return diaryService.searchAllDesc(principal.getName());
    }

    /**
     * 일기 저장
     */
    @PostMapping("/save/diary")
    public ResponseDto saveDiary(@RequestBody DiaryDto diaryDto, Principal principal) {
        diaryService.saveDiary(diaryDto, principal.getName());
        return new ResponseDto(ResponseStatus.SUCCESS);
    }

    /**
     * 일기 수정
     */
    @PostMapping("/modify/diary")
    public ResponseDto modifyDiary(@RequestBody DiaryDto diaryDto, Principal principal) {
        diaryService.modifyDiary(diaryDto, principal.getName());
        return new ResponseDto(ResponseStatus.SUCCESS);
    }

    /**
     * 일기 삭제
     */
    @PostMapping("/delete/diary/{diaryId}")
    public ResponseDto deleteDiary(@PathVariable("diaryId") Long diaryId) {
        diaryService.deleteDiary(diaryId);
        return new ResponseDto(ResponseStatus.SUCCESS);
    }

    /**
     * 파일 업로드
     */
    @PostMapping("/upload")
    public ResponseEntity<String> saveFile(@RequestParam(name = "file") List<MultipartFile> files,
                                           @RequestParam(name = "diaryId") Long diaryId) throws IOException {
        for (MultipartFile file : files) {
            System.out.println(file);
        }
        try {
            fileService.save(files, diaryId); // db에 저장
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) { // 에러 처리
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

    /**
     * 파일 띄우기
     */
    @GetMapping("/image/{diaryId}")
    public ResponseEntity<List<byte[]>> getFile(@PathVariable Long diaryId, HttpServletResponse response) {
        List<Image> images = fileRepository.findAllByDiary_DiaryId(diaryId);
        try {
            images.stream().forEach(e -> e.setPicByte(decompressBytes(e.getPicByte())));
//        image.get(0).setPicByte(decompressBytes(image.get(0).getPicByte())); // 이미지 압축 풀기
            return ResponseEntity.ok().body(images.stream().map(e -> e.getPicByte()).collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}

