package com.example.exam_board.api;

import com.example.exam_board.dto.ArticleApiDto;
import com.example.exam_board.dto.ArticleCommentDto;
import com.example.exam_board.dto.ArticleForm;
import com.example.exam_board.entity.Article;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleApiController {
    @Autowired
    ArticleApiService service;

    @GetMapping("/api/articles") // 조회
    public List<ArticleApiDto> getLists(){
        return service.viewList();
    }
    @GetMapping("/api/articles/{id}") // 단건조회
    public ResponseEntity<ArticleApiDto> getOneList(@PathVariable Long id){
        return service.getOneList(id);
    }









    @PostMapping("/api/articles")  // 추가Post
    public ResponseEntity<Article> insert(@RequestBody ArticleApiDto dto ){
        return service.insertList(dto);
    }




    @DeleteMapping("/api/articles/{id}") // 삭제
    public ResponseEntity<ArticleApiDto> delete(@PathVariable Long id){
        return service.deleteList(id);
    }




    @PatchMapping("/api/articles/{id}") // 수정
    public ResponseEntity<ArticleApiDto> Patch(@PathVariable Long id,
                                               @RequestBody ArticleApiDto dto){
        return service.patchList(id,dto);
    }
}
