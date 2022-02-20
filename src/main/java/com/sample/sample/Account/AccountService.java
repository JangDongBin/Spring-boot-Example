package com.sample.sample.Account;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepositroy accountRepository;

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

    public Account updateProcess(AccountForm AccountForm) {
        Account newAccount;

        if (AccountForm.getId() == null) {
            newAccount = Account.builder()
                        .id(null)
                        .userid(AccountForm.getUseridField())
                        .Password(AccountForm.getPasswordField())
                        .name(AccountForm.getNameField())
                        .tel(AccountForm.getTelField())
                        .email(AccountForm.getEmailField())
                        .build();
        } 
        accountRepository.save(newAccount);
        return newAccount;
    }
}
