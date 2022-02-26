package com.sample.sample.Account;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class UserAccount extends User{
    private Account account;

    public UserAccount(Account account){
        super(account.getUserid(), account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.account = account;
    }

    public UserAccount(Account account, List<SimpleGrantedAuthority> authorities){
        super(account.getUserid(), account.getPassword(), authorities);
        this.account = account;
    }
}
