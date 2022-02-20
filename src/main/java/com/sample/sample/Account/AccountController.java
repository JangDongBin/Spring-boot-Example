package com.sample.sample.Account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountRepositroy accountRepository;
    private final AccountService accountService;

    @GetMapping("/add")
    public String Account_Add(Model model, @RequestParam(required = false) Long id){
        accountService.detailProcess(model, id);
        return "account/accountAdd";
    }

    @PostMapping("/add")
    public String Post_Account_Add(){
        return "";
    }
}
