package com.sample.sample.Account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepositroy extends JpaRepository<Account, Long> {
    Account findByUserid(String id);
}
