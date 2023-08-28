package com.example.exam_board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {
    @Bean
    public AuditorAware<String> auditorAware(){
        //return ()-> Optional.of("min");  //TODO: 스프링 시큐리티로 인증 기능을 붙이게 될 때 수정..
        // 앞으로 인증기능 전까지 create 랑 모디에이티드 어쩌고를 실행할때 사용자 정보를 얘로 쓰겠다는말..
        return new AuditorAwareImpl();
    }
}
