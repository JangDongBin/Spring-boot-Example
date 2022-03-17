package com.sample.sample.account;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    //private final JavaMailSender javaMailSender;    
    private final TempMailSender tempMailSender;    
    //@Value("${spring.mail.username}")
    //private final String sendFrom;

    public void mailSend(String email, String username, String token, Long useridval) {
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
    
        tempMailSender.send(simpleMailMessage);
        //System.out.println("\n\n\n\n\n\n메일전송\n\n\n\n\n");
    }

    // 회원가입 폼
    public void account_add_Process(Model model) {
        model.addAttribute("accountForm", new AccountForm());
    }

    // 회원가입 세이브
    public void updateProcess(AccountForm AccountForm) {
        Account newAccount;
        List<Role> roles = new ArrayList<>();
        List<Role> temp = new ArrayList<>();

        // ROLE Table 전체 올림차순으로 정렬해서 변수에 대입
        roles = roleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        // 기본 인증 권한
        temp.add(0, roles.get(0));

        if (AccountForm.getId() == null) {
            newAccount = Account.builder()
                    .id(null)
                    .userid(AccountForm.getUseridField())
                    .password(passwordEncoder.encode(AccountForm.getPasswordField()))
                    .name(AccountForm.getNameField())
                    .email(AccountForm.getEmailField())
                    .tel(AccountForm.getTelField())
                    .emailTokenSendAt(LocalDateTime.now())
                    .roles(temp)
                    .build();
        } else {
            newAccount = Account.builder()
                    .id(AccountForm.getId())
                    .userid(AccountForm.getUseridField())
                    .password(passwordEncoder.encode(AccountForm.getPasswordField()))
                    .name(AccountForm.getNameField())
                    .email(AccountForm.getEmailField())
                    .tel(AccountForm.getTelField())
                    .emailTokenSendAt(LocalDateTime.now())
                    .roles(temp)
                    .build();
        }
        newAccount.creationEmailTokenValue();
        newAccount.setEmailTokenfalse();
        
        accountRepository.save(newAccount);
        mailSend("ggb04212@naver.com", newAccount.getName(),
        newAccount.getEmailToken(), newAccount.getId());
        
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
                account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        
    }

    //회원 권한 수정
    public void auth_update(List<Long> auth_array, String userid) {
        List<Role> accountRoles = roleRepository.findByIdIn(auth_array);
        Account user = accountRepository.findByUserid(userid);
        user.setRoles(accountRoles); //set 하는 순간 권한에 대한 값들이 변경되므로 따로 save 해줄 필요가 없음.

        //System.out.println(user);
    }

    public void signupProcess(Model model, String token, Long userid) {
        if (userid != null) {
            Optional<Account> Accountop = accountRepository.findById(userid);
            if (Accountop.get().getEmailToken().equals(token)) {
                Account newAccount = Accountop.get();
                
                newAccount = Account.builder()
                        .id(newAccount.getId())
                        .userid(newAccount.getUserid())
                        .password(newAccount.getPassword())
                        .name(newAccount.getName())
                        .email(newAccount.getEmail())
                        .tel(newAccount.getTel())
                        .emailToken(newAccount.getEmailToken())
                        .emailTokenVaild(true)                        
                        .build(); 
                accountRepository.save(newAccount);     
                model.addAttribute("name", newAccount.getName());
                model.addAttribute("id", newAccount.getId());
                System.out.println("인증완료");
            }else model.addAttribute("error", "worng token!");

        }
    }

    public void sendSignUpConfirmEmail(Account account) {

        mailSend("ggb04212@naver.com", account.getName(),
        account.getEmailToken(), account.getId()); //메일 보내고
        account.setEmailTokenSendtime(); // 보낸시간 찍고
    }

}