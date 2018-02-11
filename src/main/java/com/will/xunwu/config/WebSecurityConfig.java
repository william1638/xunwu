package com.will.xunwu.config;

import com.will.xunwu.security.AuthProvider;
import com.will.xunwu.security.LoginUrlEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
               .and()
               .logout()
               .logoutUrl("/logout")
               .logoutSuccessUrl("/logout/page")
               .deleteCookies("JSESSIONID")
               .invalidateHttpSession(true)
               .and()
               .exceptionHandling()
               .authenticationEntryPoint(urlEntryPoint())
               .accessDeniedPage("/403");
       http.csrf().disable();
       http.headers().frameOptions().sameOrigin();

    }

    /**
     * 自定义认证策略
     */
    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider()).eraseCredentials(true);
    }

    @Bean
    public AuthProvider authProvider() {
        return new AuthProvider();
    }

    @Bean
    public LoginUrlEntryPoint urlEntryPoint() {
        return new LoginUrlEntryPoint("/user/login");
    }


}
