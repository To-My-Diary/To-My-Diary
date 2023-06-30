package com.example.spring_study.Controller;

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

