package com.sample.sample.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .mvcMatchers("/security/test1").permitAll()
            .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring()
            .mvcMatchers("/css/**")
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
