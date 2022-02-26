package com.sample.sample.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RoleRepository extends JpaRepository<Role, Long>{
}
