package com.sample.sample.main;

import com.sample.sample.account.Account;
import com.sample.sample.account.CurrentUser;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/")
    public String index(@CurrentUser Account account, Model model) {

        if(account != null){
            model.addAttribute(account);
        }
        return "index";
    }
}