package com.example.exam_board.entity;

import com.example.exam_board.dto.ArticleCommentDto;
import com.example.exam_board.dto.ArticleForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Article extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "articleId")
    private Long id;

    @JoinColumn(name = "userId")
    @ManyToOne
    private UserAccount userAccount; // 유저 정보 ( ID )

    @Column(nullable = false)
    private String title; // 제목
    @Column(nullable = false, length = 10000)
    private String content;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleComment> articleCommentList = new ArrayList<>();
    // 특정 게시글이 삭제가 되면 댓글도 삭제 되어야 함.. 연관 돼 있는걸 따라가서 삭제하는것이 cascade..
    // orphanRemoval 고아객체 삭제...댓글중..해당하는 댓글만 남아있는것도 청소.......뭔소린지 모르겠음..
    // 유저 관련된 부분은 cascade 주면 안됨..탈퇴하면 다 날라감

}
