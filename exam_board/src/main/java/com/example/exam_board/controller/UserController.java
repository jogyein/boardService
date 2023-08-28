package com.example.exam_board.controller;

import com.example.exam_board.dto.UserCreateForm;
import com.example.exam_board.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/login")
    public String login(){
        return "login_form";
    }

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm){
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "signup_form";
        }
        if(userCreateForm.getPassword1() != userCreateForm.getPassword2()){
            bindingResult.rejectValue("password2", "passwordIncorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
        }
        // 중복회원 가입 처리
        try{
            userService.createUser(userCreateForm.getUsername(),userCreateForm.getPassword1(),
                    userCreateForm.getEmail(), userCreateForm.getNickname());
        } catch (DataIntegrityViolationException e){
            bindingResult.reject("signupFailed","이미 등록된 사용자입니다.");
            return "signup_form";
        }
        return "login_form";
    }

}
