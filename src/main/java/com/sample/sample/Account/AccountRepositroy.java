package com.sample.sample.Account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepositroy extends JpaRepository<Account, Long>{
    //Page<Account> findByUseridContainingOrTitleContainingOrderByIdDesc(String searchString, String searchString2,
    //        Pageable pageable);
}
