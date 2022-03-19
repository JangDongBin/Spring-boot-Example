package com.sample.sample.config;

import com.sample.sample.account.AccountService;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //private AccountService accountService;
    //private LoginSuccessHandler loginSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/", "/account/login", "/account/add", "/account/signup").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/account/login")
                    .defaultSuccessUrl("/")
                    .permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/");

        http.rememberMe()
            .key("oingdaddy!")
            .rememberMeParameter("remember-me")
            .tokenValiditySeconds(86400*30);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/css/**")
                .mvcMatchers("/node_modules/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        // 임의의 위치에 대해서 검증자체를 하지않음.
        // 필터 자체를 없애버림
    }

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
