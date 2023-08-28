package com.example.exam_board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing // db 수정사항 생겼을때 감시하고 있다가.. 자동으로... 해당필드에... 알아서 넣어줌.........
public class ExamBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamBoardApplication.class, args);
	}

}
