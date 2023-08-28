package com.example.exam_board.repository;

import com.example.exam_board.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityRepository extends JpaRepository <TestEntity,Long> {
}
