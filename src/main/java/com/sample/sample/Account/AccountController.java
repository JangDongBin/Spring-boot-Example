package com.sample.sample.account;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Sort;
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

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AccountService accountService;
    private final AccountFormValidator accountFormValidator;

    @InitBinder("AccountForm")
    public void InitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(accountFormValidator);
    }

    // 회원가입
    @GetMapping("/add")
    public String account_Add(Model model) {
        accountService.account_add_Process(model);
        return "account/add";
    }

    // 회원가입처리
    @PostMapping("/add")
    public String account_add_Post(@Valid AccountForm accountForm,  Errors errors , Model model) {
        if (errors.hasErrors()) {
            return "account/add";
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
    public String auth_update(@RequestParam(required = false) String userid, Model model) {
        if (userid != null) {
            Account searchAccount = accountRepository.findByUserid(userid);

            if (searchAccount == null) {
                model.addAttribute("message", "일치하는 아이디가 없습니다." );
            } else{
                model.addAttribute("auth", searchAccount.getRoles());
            }
        } else {
            model.addAttribute("message", "아이디를 넣어주세요!");
        }

        model.addAttribute("allAuth", roleRepository.findAll(Sort.by(Sort.Direction.ASC,"id")));
        model.addAttribute("userid", userid);
        return "account/AuthUpdate";
    }

    @PostMapping("/update")
    public String auth_update_Post(@RequestParam(value = "arr[]") List<Long> arr, String userid) {
        // ajax를 통해 넘어온 배열 데이터 선언
        accountService.auth_update(arr, userid);
        return "redirect:/account/update";
        //account/AuthUpdate??userid="+userid;
    }

    // 권한확인
    @GetMapping("/principal")
    public String principal() {
        return "account/principal";
    }

}
