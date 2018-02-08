package com.will.xunwu.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    /**
     * HTTP权限控制
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()
               .antMatchers("/admin/login").permitAll() // 管理员登录入口
               .antMatchers("/static/**").permitAll() // 静态资源
               .antMatchers("/user/login").permitAll() // 用户登录入口
               .antMatchers("/admin/**").hasRole("ADMIN")
               .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
               .antMatchers("/api/user/**").hasAnyRole("ADMIN",
               "USER")
               .and()
               .formLogin()
               .loginProcessingUrl("/login")
               .and();
       http.csrf().disable();
       http.headers().frameOptions().sameOrigin();

    }



}
