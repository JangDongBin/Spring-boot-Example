package com.sample.sample.account;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Long>{
    Account findByUserid(String userid);
}
