package com.example.exam_board.controller;

import com.example.exam_board.dto.ArticleCommentDto;
import com.example.exam_board.dto.ArticleForm;
import com.example.exam_board.dto.SearchParam;
import com.example.exam_board.entity.Article;
import com.example.exam_board.service.ArticleService;
import com.example.exam_board.service.PaginationService;
import com.example.exam_board.service.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("articles")
public class BoardController {
    @Autowired
    PaginationService paginationService;
    @Autowired
    ArticleService articleService;
    @GetMapping("/list")
    public String viewList(Model model) {
        List<ArticleForm> dtoLists = articleService.viewList();
        model.addAttribute("dtoLists", dtoLists);
        return "view";
    }
    @GetMapping("/lists")
    public String boardList(Model model, @RequestParam(value = "searchType", required = false) String searchType,
                            @RequestParam(value = "searchValue", required = false) String searchValue,
                            @PageableDefault(page = 0, size = 10, sort = "createdAt",
                                    direction = Sort.Direction.DESC)
                            Pageable pageable) {

        SearchParam searchParam = new SearchParam();
        searchParam.setSearchType(searchType);
        searchParam.setSearchValue(searchValue);

        Page<Article> paging = (Page<Article>) articleService.boardList(pageable,
                searchType, searchValue);

        //페이지블럭 처리
        //1을 더해주는 이유는 pageable은 0부터라 1을 처리하려면 1을 더해서 시작해주어야 한다.
        Integer nowPage = paging.getPageable().getPageNumber() + 1;
        Long totalPage = paging.getTotalElements();

        Paginator paginator = new Paginator(5, 10, totalPage);

        for (int i = 1; i <= paginator.getTotalLastPageNum(); i++) {
            System.out.println(paginator.getElasticBlock(i));
        }
        //-1값이 들어가는 것을 막기 위해서 max값으로 두 개의 값을 넣고 더 큰 값을 넣어주게 된다.

        Map<String, Object> sPage = paginator.getElasticBlock(nowPage);
        int startPage = (int) sPage.get("blockFirstPageNum");
        int endPage = (int) sPage.get("blockLastPageNum");

        model.addAttribute("paging", paging);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("param", searchParam);

        return "view";
    }
    // 글쓰기
    @GetMapping("/new") // x
    @PreAuthorize("isAuthenticated()")
    public String newArticleForm(Model model){
        model.addAttribute("dto", new ArticleForm());
        return "articles/new";
    }
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String newArticleSave(ArticleForm dto, Principal principal){
        Long id;
        id = articleService.articleSave(dto, principal);
        return "redirect:/articles/lists" ;
    }
    // 게시물 보기
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String show(@PathVariable Long id, Model model){
        Article article = articleService.getOneArticle(id);

        model.addAttribute("articleDto", new ArticleCommentDto()); // 댓글 달 수 있는 dto 보냄
        model.addAttribute("dto", article);
        return "articles/detail";
    }
    // 게시물 수정하기
    @GetMapping("/{id}/update") // x
    @PreAuthorize("isAuthenticated()")
    public String update(@PathVariable Long id, Model model){ // update창에 id를 뿌려줘야 하니까 PathVariable 어노테이션 사용
        Article article = articleService.getOneArticle(id);
        model.addAttribute("dto", article);
        return "articles/update";
    }
    @PostMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public String updateArticle(@RequestParam("id") Long id, ArticleForm dto){
        articleService.articleUpdate(dto, id);
        return "redirect:/articles/lists" ;
    }
    // 게시물 삭제하기
    @GetMapping("/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        articleService.deleteArticle(id);
        redirectAttributes.addFlashAttribute("msg", "삭제 되었습니다.");
        return "redirect:/articles/lists" ;
    }
    // 게시물 댓글달기
    @PostMapping("/{id}/articleComment")
    @PreAuthorize("isAuthenticated()")
    public String insertArticleComment(@PathVariable Long id, ArticleCommentDto articleDto, Principal principal){
        articleService.articleCommentSave(id, articleDto,principal);
        return "redirect:/articles/" + id ;
    }
    // 게시물 댓글 삭제하기
    @PostMapping("/{id}/articleComments/{article-comment-id}/delete")
    public String deleteArticleComment(@PathVariable("id") Long articleId,
                                       @PathVariable("article-comment-id") Long articleCommentId){
        articleService.articleCommentDelete(articleCommentId);
        return "redirect:/articles/" + articleId;
    }
}