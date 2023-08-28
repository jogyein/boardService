/* package com.example.exam_board.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class) // 엔티티가 수정이 될 때 main에서 설정한 감시..를..가능하도록 해주는...그것을 듣고있는것...
@MappedSuperclass
@Data
public class BaseEntity {
    @CreatedDate // 생성날짜를 자동으로 만들어서 created에 넣어줌
    LocalDateTime createdAt;
    @LastModifiedDate // 최종적으로 수정된 날짜.시각을 update에 줌 , save 되거나 update 될때.. main에 EnableJpa어쩌고라는 애가 해줌
    LocalDateTime updatedAt;
} */
