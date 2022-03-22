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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;

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

    // 인증메일 재전송
    @GetMapping("/resend")
    public String mail_resend_index() {
        return "account/emailcheck";
    }
    
    // 인증메일 재전송
    @GetMapping("/resend_email")
    public String mail_resend(@CurrentUser Account account, Model model) {
        if(!account.canSendConfirmEmail()){
            model.addAttribute("error", "인증 이메일은 1시간에 한번만 전송할 수 있습니다.");
            model.addAttribute("email", account.getEmail());
            return "account/emailcheck";
        } else if(account.canSendConfirmEmail()) {
            model.addAttribute("error", "메일이 전송되었습니다!");
            accountService.sendSignUpConfirmEmail(account);
            return "account/emailcheck";
        }

        return "redirect:/";
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
    @ResponseBody
    public Account auth_update_Post(@RequestBody List<Long> arr, @RequestParam(value="userid") String search_userid) {
        System.out.println("\n");
        System.out.println(arr);
        System.out.println(search_userid);
        return accountService.auth_update(arr, search_userid);
    }

    // 권한확인
    @GetMapping("/principal")
    public String principal() {
        return "account/principal";
    }
    

}
