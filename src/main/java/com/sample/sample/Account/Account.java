package com.sample.sample.account;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PostPersist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "ACCOUNT")
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String userid;

    private String password;

    private String name;

    private String tel;

    private String email;

    private String emailToken;

    private LocalDateTime emailTokenSendAt;

    @Column(columnDefinition = "boolean default false")
    private Boolean emailTokenVaild;

    @PostPersist
    public void creationEmailTokenValue() { // 인증 값 생성
        this.emailToken = UUID.randomUUID().toString();
    }

    public void setEmailTokentrue() {
        this.emailTokenVaild = true;
    }
    public void setEmailTokenfalse() {
        this.emailTokenVaild = false;
    }

    public void setEmailTokenSendtime() { 
        this.emailTokenSendAt = LocalDateTime.now();
    }

    public boolean canSendConfirmEmail(){
        //return this.emailTokenSendAt.isBefore(LocalDateTime.now().minusHours(1));
        return true;//테스트용 트루임 주석처리하세요
    }
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_role_join_table", joinColumns = @JoinColumn(name = "accountId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    public List<Role> roles = new ArrayList<>();
}
