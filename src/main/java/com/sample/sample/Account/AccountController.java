package com.sample.sample.Account;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.Errors;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountRepositroy accountRepository;
    private final AccountService accountService;
    private final AccountFormValidator accountFormValidator;

    @InitBinder("accountForm")
    public void InitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(accountFormValidator);
    }

    @GetMapping("/add")
    public String Account_Add(Model model, @RequestParam(required = false) Long id) {
        accountService.detailProcess(model, id);
        return "account/accountAdd";
    }

    @PostMapping("/add")
    public String Post_Account_Add(@Valid AccountForm accountForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "account/accountAdd";
        }
        // Form 값 확인
        //System.out.println(accountForm.getUseridField());
        //System.out.println(accountForm.getPasswordField());
        //System.out.println(accountService.passwordEncoderProcess(accountForm.getPasswordField()));//pw암호화 확인
        //System.out.println(accountForm.getNameField());
        //System.out.println(accountForm.getTelField());
        //System.out.println(accountForm.getEmailField());
        
        accountService.updateProcess(accountForm);

        return "account/accountCheck";
    }

    @GetMapping("/main")
    public String Account_main(Model model, @RequestParam(required = false) Long id) {
        accountService.detailProcess(model, id);
        return "account/accountAdd";
    }

    @GetMapping("/login")
    public String Account_login(Model model, @RequestParam(required = false) Long id) {        
        return "account/accountLogin";
    }

    @GetMapping("/signup")
    public String Account_signup(Model model, @RequestParam(required = false) Long id) {        
        
        
        return "account/accountLogin";
    }



}
