package com.sample.sample.config;

import javax.annotation.security.PermitAll;

import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        http.authorizeRequests()
            .mvcMatchers("/security/test1").permitAll()
            .anyRequest().authenticated();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // TODO Auto-generated method stub
        
        web.ignoring() 
            .mvcMatchers("css/**")
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
            //임의의 위치에 대해서 검증자체를 하지않음.
            //필터 자체를 없애버림
    }



}
