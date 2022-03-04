package com.sample.sample.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
    private final JavaMailSender javaMailSender;
    // @Value("${spring.mail.username}")
    // private final String sendFrom;

    public void mailSend(String email, String username, String token, String useridval) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("장봐요 인증메일입니다.");
        simpleMailMessage.setText(
                username + "의 인증메일 입니다.\n\n\nlocalhost:8080/account/signup?token=" + token + "&userid=" + useridval);

        javaMailSender.send(simpleMailMessage);
        // System.out.println("\n\n\n\n\n\n메일전송\n\n\n\n\n");
    }

    // 회원가입 / 회원 정보 보기
    public void detailProcess(Model model, Long id) {
        if (id != null) {
            Optional<Account> account = accountRepository.findById(id);

            if (account.isPresent()) {
                AccountForm accountForm = AccountForm.builder()
                        .id(account.get().getId())
                        .useridField(account.get().getUserid())
                        .passwordField(null)
                        .nameField(account.get().getName())
                        .emailField(account.get().getEmail())
                        .build();

                model.addAttribute("accountForm", accountForm);
            }
        } else {
            model.addAttribute("accountForm", new AccountForm());
        }
    }

    // 회원가입
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
                    .roles(temp)
                    .build();
        } else {
            newAccount = Account.builder()
                    .id(AccountForm.getId())
                    .userid(AccountForm.getUseridField())
                    .password(passwordEncoder.encode(AccountForm.getPasswordField()))
                    .name(AccountForm.getNameField())
                    .email(AccountForm.getEmailField())
                    .roles(temp)
                    .build();
        }
        newAccount.creationEmailTokenValue();
        // 이메일 변경 해주세요 제발 히잉
        // mailSend("ggb04212@naver.com", newAccount.getName(),
        // newAccount.getEmailToken(), newAccount.getUserid());

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
                account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
    }

    public void auth_update(String[] auth_array) {
        //현재 세션값을 기준으로 로그인 되어있는 사용자 정보를 불러옴
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        List<Role> roles = new ArrayList<>();
        List<Role> temp = new ArrayList<>();

        roles = roleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        try {
            if (auth_array != null && auth_array.length > 0) {
                for (int i = 0; i < auth_array.length; i++) {
                    if (auth_array[i].equals("ROLE_TEMPORARY_USER")) {
                        temp.add(i, roles.get(0));
                    } else if (auth_array[i].equals("ROLE_USER")) {
                        temp.add(i, roles.get(1));
                    } else if (auth_array[i].equals("ROLE_ADMIN")) {
                        temp.add(i, roles.get(2));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // userid 찾아오기
        String userName = null;
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userName = userDetails.getUsername();
        }
        Account temp1 = accountRepository.findByUserid(userName);
        Account resave_account;
        resave_account = Account.builder()
                .id(temp1.getId())
                .userid(temp1.getUserid())
                .password(temp1.getPassword())
                .name(temp1.getName())
                .email(temp1.getEmail())
                .roles(temp)
                .build();
        accountRepository.save(resave_account);
        System.out.println(resave_account);
    }
}
