package com.sample.sample.account;

import java.util.ArrayList;
import java.util.List;

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

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

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
