package com.example.configserver;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurity  extends WebSecurityConfigurerAdapter {
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests().antMatchers( "/**").permitAll();
        httpSecurity.csrf().disable();
    }
}
