package com.example.exam_board.controller;

import com.example.exam_board.dto.TestForm;
import com.example.exam_board.entity.TestEntity;
import com.example.exam_board.service.TestApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestApiController {
    @Autowired
    TestApiService apiService;
    @GetMapping("/api/lists") // 조회
    public List<TestEntity> getLists(){
        return apiService.viewList();
    }

    @GetMapping("/api/lists/{id}") // 단건조회
    public ResponseEntity<TestEntity> getOneList(@PathVariable Long id){
        return apiService.getOneList(id);
    }

    @PostMapping("/api/list") // 추가
    public TestEntity insert(@RequestBody TestForm form){
        return apiService.insertList(form);
    }

    @DeleteMapping("/api/lists/{id}") // 삭제
    public ResponseEntity<TestEntity> delete(@PathVariable Long id){
        return apiService.deleteList(id);
    }

    @PatchMapping("/api/lists/{id}")  // 수정
    public ResponseEntity<TestEntity> Patch(@PathVariable Long id,
                                            @RequestBody TestForm form){
        return apiService.patchList(id,form);
    }

    /*
    조회   /api/articles   --  Get
    단건조회   /api/articles/{id}  -- Get
    수정         /api/articles/{id}  -- Patch
    삭제         /api/articles/{id}  -- Delete
    추가      /articles/article     -- Post
    댓글전체조회 /api/articles/{id}/comments -- GET
       댓글단건조회 /api/articles/{id}/comments/{article_id} -- GET
         댓글추가  /api/articles/{id}/comment -- Post/Put
         댓글삭제  /api/articles/{id}/comments/{a_id} -- Delete
         댓글수정  /api/articles/{id}/comments/{a_id} -- Patch
     */

}
