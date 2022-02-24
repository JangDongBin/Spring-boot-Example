package com.sample.sample.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "ACCOUNT_ROLE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRole {

    @Id @GeneratedValue
    private Long id;

    private String rolename;

    @Column(unique = true)
    private String role;

}
