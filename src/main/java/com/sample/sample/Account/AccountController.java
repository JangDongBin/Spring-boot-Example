package com.sample.sample.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    //회원가입창
    @GetMapping("/sign-up")
    public String signUpPage(){
        return "login/sign-up";
    }

    //회원가입처리
    @PostMapping("/sign-up")
    public String signUp(SignUpForm signUpForm){
        System.out.println("form = "+signUpForm);
        accountService.signUp(signUpForm);
        return "redirect:/";
    }

    //권한확인
    @GetMapping("/principal")
    public String principal(){  
        return "login/principal";
    }
    
}
