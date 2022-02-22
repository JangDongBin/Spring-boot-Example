package com.sample.sample.Account;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    enum Authority_Value { // 계정 등급
        NoAuth,
        Iron,
        Bronze,
        Silver,
        Gold
    }

    private final AccountRepositroy accountRepository; // 계정 레파지토리
    private final RoleRepositroy roleRepositroy; // 권한 레파지토리
    private final PasswordEncoder passwordEncoder;

    // 계정 상세보기
    public void detailProcess(Model model, Long id) {
        if (id != null) {
            Optional<Account> account = accountRepository.findById(id);

            if (account.isPresent()) {
                AccountForm accountForm = AccountForm.builder()
                        .id(account.get().getId())
                        .useridField(account.get().getUserid())
                        .passwordField(account.get().getPassword())
                        .nameField(account.get().getName())
                        .telField(account.get().getTel())
                        .emailField(account.get().getEmail())
                        .build();
                
                model.addAttribute("accountForm", accountForm);
            }
        } else {
            model.addAttribute("accountForm", new AccountForm());
        }
    }

    // 계정 업데이트
    public Account updateProcess(AccountForm AccountForm) {
        Account newAccount;

        if (AccountForm.getId() == null) {
            System.out.println("1");
            newAccount = Account.builder()
                    .id(null)
                    .userid(AccountForm.getUseridField())
                    .password(passwordEncoder.encode(AccountForm.getPasswordField()))
                    .name(AccountForm.getNameField())
                    .tel(AccountForm.getTelField())
                    .email(AccountForm.getEmailField())
                    .build();
        } else {
            System.out.println("2");
            newAccount = Account.builder()
                    .id(AccountForm.getId())
                    .userid(AccountForm.getUseridField())
                    .password(passwordEncoder.encode(AccountForm.getPasswordField()))
                    .name(AccountForm.getNameField())
                    .tel(AccountForm.getTelField())
                    .email(AccountForm.getEmailField())
                    .build();
        }

        accountRepository.save(newAccount);
        
        System.out.println(role_update_process(newAccount, 2));
        return newAccount;
    }

    // 권한 업데이트
    public Role role_update_process(Account newAccount, int level) {
        Role newRole;
        if (newAccount.getId() == null) {
            newRole = Role.builder()
                    .id(null)
                    .authority(authority_value_setting(level)) // 권한 등급 설정
                    .build();
        } else {
            newRole = Role.builder()
                    .id(newAccount.getId())
                    .authority(authority_value_setting(level)) // 권한 등급 설정
                    .build();
        }

        roleRepositroy.save(newRole);
        return newRole;
    }

    // 권한 등급 설정
    public String authority_value_setting(int auth_value) {
        Authority_Value value_arr[] = Authority_Value.values();

        StringBuilder stringBuilder = new StringBuilder();
        String temp_auth_value;
        String final_auth_value = "";

        for (int i = 1; i <= auth_value; i++) {
            temp_auth_value = value_arr[i].toString();
            stringBuilder.append(temp_auth_value);
            if(i != auth_value)
                stringBuilder.append(", ");
            final_auth_value = stringBuilder.toString();
        }

        return final_auth_value;
    }

    
}
