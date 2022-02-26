package com.sample.sample.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/add")
    public String Account_Add(Model model) {
        accountService.detailProcess(model);
        return "account/accountAdd";
    }

    // 회원가입처리
    @PostMapping("/add")
    public String Account_add_Post(AccountForm accountForm, Model model, Errors errors) {
        if (errors.hasErrors()) {
            return "account/accountAdd";
        }
        accountService.updateProcess(accountForm);
        return "account/accountCheck";
    }
    /*
     * @GetMapping("/main")
     * public String Account_main(Model model, @RequestParam(required = false) Long
     * id) {
     * accountService.detailProcess(model, id);
     * return "account/accountAdd";
     * }
     * 
     * @GetMapping("/login")
     * public String Account_login(Model model, @RequestParam(required = false) Long
     * id) {
     * return "account/accountLogin";
     * }
     * 
     * //권한확인
     * 
     * @GetMapping("/principal")
     * public String principal(){
     * return "login/principal";
     * }
     * 
     */

}
