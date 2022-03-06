package com.sample.sample.account;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.InitBinder;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountFormValidator accountFormValidator;

    @InitBinder("AccountForm")
    public void InitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(accountFormValidator);
    }

    // 회원가입 / 회원정보 수정
    @GetMapping("/add")
    public String account_Add(Model model, @RequestParam(required = false) Long id) {
        accountService.detailProcess(model, id);
        return "account/Add";
    }

    // 회원가입처리
    @PostMapping("/add")
    public String account_add_Post(@Valid AccountForm accountForm, Model model, Errors errors) {
        if (errors.hasErrors()) {
            return "account/Add";
        }
        accountService.updateProcess(accountForm);
        return "redirect:/";
    }

    // 로그인
    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    // 인증메일 Check
    @GetMapping("/signup")
    public String token_check(Model model, @RequestParam String token, @RequestParam Long userid) {

        accountService.signupProcess(model, token, userid);
        return "account/tokencheck";
    }

    // 권한 업데이트
    @GetMapping("/update")
    public String auth_update(Model model) {
        //accountService.auth_update();
        return "account/AuthUpdate";
    }

    @PostMapping("/update")
    public String auth_update_Post(HttpServletRequest req) {
        // ajax를 통해 넘어온 배열 데이터 선언
        String[] arrStr = req.getParameterValues("auth");
        accountService.auth_update(arrStr);
        return "redirect:/";
    }

    // 권한확인
    @GetMapping("/principal")
    public String principal() {
        return "account/principal";
    }

}
