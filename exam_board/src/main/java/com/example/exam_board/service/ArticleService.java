package com.example.exam_board.service;

import com.example.exam_board.dto.ArticleCommentDto;
import com.example.exam_board.dto.ArticleForm;
import com.example.exam_board.entity.Article;
import com.example.exam_board.entity.ArticleComment;
import com.example.exam_board.entity.UserAccount;
import com.example.exam_board.repository.ArticleRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    EntityManager em;
    @Autowired
    ArticleRepository articleRepository;
    @Transactional
    public Long articleSave(ArticleForm articleForm, Principal principal){
        UserAccount account = em.find(UserAccount.class, principal.getName());

        Article article = Article.builder()
                .title(articleForm.getTitle())
                .content(articleForm.getContent())
                .userAccount(account)
                .build();
        em.persist(article);
        return article.getId();
    }
    @Transactional
    public void articleUpdate(ArticleForm articleForm, Long id){
        Article article = em.find(Article.class, id);
        article.setTitle(articleForm.getTitle());
        article.setContent(articleForm.getContent());
        em.persist(article);
    }
    public List<ArticleForm> viewList() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleForm> dtoLists = new ArrayList<>();
        for(Article article : articles){
            ArticleForm articleForm = ArticleForm.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .build();
            dtoLists.add(articleForm);
        }
        dtoLists.forEach(article -> System.out.println(article));
        return dtoLists;
    }
    public Page<Article> boardList(Pageable pageable, String type, String keyword) {
        if(type==null || keyword.isBlank()){
            return articleRepository.findAll(pageable);
        }
        switch (type){
            case "title" :
                return articleRepository.findByTitleContains(keyword, pageable);
            case "content" :
                return articleRepository.findByContentContains(keyword, pageable);
            case "userId" :
                return articleRepository.findByUserAccount_UserIdContains(keyword, pageable);
            case "nickname" :
                return articleRepository.findByUserAccount_NicknameContains(keyword, pageable);
            default:
                //기존 List<Board>값으로 넘어가지만 페이징 설정을 해주면 Page<Board>로 넘어갑니다.
                return articleRepository.findAll(pageable);
        }
    }



    public Article getOneArticle(Long id) {
        Article article = em.find(Article.class, id);
        return article;
    }
    @Transactional
    public void deleteArticle(Long id) {
        Article article = em.find(Article.class, id);
        em.remove(article);
    }
    @Transactional
    public void articleCommentSave(Long id, ArticleCommentDto articleDto, Principal principal) {
        UserAccount account = em.find(UserAccount.class, principal); // 가상 사용자 찾아옴
        Article article = em.find(Article.class, id); // 게시글 하나 가져옴 ..
        ArticleComment articleComment = new ArticleComment(); // 새로운 댓글 생성
        articleComment.setArticle(article); // 아티클하나 셋팅...누구의 댓글인지 알기위한것
        articleComment.setUserAccount(account); // 누가 썼는지 댓글 주인
        articleComment.setContent(articleDto.getContent()); // 컨텐트 집어넣음

        article.getArticleCommentList().add(articleComment);

        em.persist(articleComment);
    }
    @Transactional
    public void articleCommentDelete(Long articleCommentId) {
        ArticleComment articleComment = em.find(ArticleComment.class, articleCommentId);
        em.remove(articleComment);
    }
}