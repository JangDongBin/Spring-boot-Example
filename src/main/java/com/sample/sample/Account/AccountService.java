package com.sample.sample.account;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JavaMailSender javaMailSender;    
    //@Value("${spring.mail.username}")
    //private final String sendFrom;

    public void mailSend(String email,String username, String token, String useridval) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("장봐요 인증메일입니다.");
        /* simpleMailMessage.setText(username + "의 인증메일 입니다.\n\n\n<a href= 'localhost:8080/account/signup?token=" + token + "&userid=" + useridval + "'</a>"); */
        simpleMailMessage.setText(new StringBuffer().append("<h1>[이메일 인증]</h1>")
                .append(username)
                .append("님! 장봐요에 가입해주셔서 감사합니다!")
				.append("<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>")
				.append("<a href='http://localhost:8080/account/signup?token=")
				.append(token)
				.append("&userid=")
				.append(useridval)
				.append("' target='_blenk'>이메일 인증 확인</a>")
				.toString());
    
        javaMailSender.send(simpleMailMessage);
        System.out.println("\n\n\n\n\n\n메일전송\n\n\n\n\n");
    }

    // 계정 model
    public void detailProcess(Model model) {
        model.addAttribute("accountForm", new AccountForm());
    }

    // 계정 업데이트
    public void updateProcess(AccountForm AccountForm) {

        List<Role> roles = new ArrayList<>();

        roles = roleRepository.findAll();

        Account newAccount = Account.builder()
                .userid(AccountForm.getUseridField())
                .password(passwordEncoder.encode(AccountForm.getPasswordField()))
                .name(AccountForm.getNameField())
                .email(AccountForm.getEmailField())
                .roles(roles)                
                .build();

        newAccount.creationEmailTokenValue();
        //이메일 변경 해주세요 제발 히잉
        mailSend("ggb04212@naver.com",newAccount.getName() ,newAccount.getEmailToken(), newAccount.getUserid()); 
        accountRepository.save(newAccount);

    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        Account account = accountRepository.findByUserid(userid);
        if (account == null) {
            throw new UsernameNotFoundException(userid);
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        account.getRoles().forEach(e -> {
            authorities.add(new SimpleGrantedAuthority(e.getAuthority()));
        });

        return new UserAccount(account, authorities);
    }

    public void login(Account account) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(new UserAccount(account),
                account.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
    }

}
