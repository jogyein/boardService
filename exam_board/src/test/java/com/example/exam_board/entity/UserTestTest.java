/* package com.example.exam_board.entity;

import com.example.exam_board.repository.UserTestRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTestTest {
    @Autowired
    UserTestRepository userTestRepository;

    @Test
    @DisplayName("UserTest 입력")
    void inputUserTest(){
        UserTest test = new UserTest();
        test.setName("이순신");
        userTestRepository.save(test);
    }
} */