package com.example.exam_board.service;

import com.example.exam_board.entity.UserAccount;
import com.example.exam_board.entity.constant.UserRole;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EntityManager em;

    @Transactional
    public void createUser(String userId, String userPassword,
                           String email, String nickName){
        UserAccount account = new UserAccount();
        account.setUserId(userId);
        account.setUserPassword(passwordEncoder.encode(userPassword));
        account.setEmail(email);
        account.setNickname(nickName);
        if("ADMIN".equals(userId.toUpperCase())){
            account.setUserRole(UserRole.ADMIN);
        } else {
            account.setUserRole(UserRole.USER);
        }
        em.persist(account);
    }
}
