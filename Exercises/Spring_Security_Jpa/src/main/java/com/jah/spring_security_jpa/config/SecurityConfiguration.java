/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.config;

import com.jah.spring_security_jpa.handler.CustomAuthenticationFailureHandler;
import com.jah.spring_security_jpa.handler.MyAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author drjal
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/register").hasRole("ADMIN")
                .antMatchers("/userList").hasRole("ADMIN")
                .antMatchers("/updateUser").hasRole("ADMIN")
                .antMatchers("/updatePost").hasRole("ADMIN")
                .antMatchers("/defaultImage").hasRole("ADMIN")
                .antMatchers("/tags").hasRole("ADMIN")
                .antMatchers("/updateTag").hasRole("ADMIN")
                .antMatchers("/editPosts").hasRole("ADMIN")
                .antMatchers("/editAdminProfile").hasRole("ADMIN")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("ADMIN", "USER")
                .and().formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/default")
                .failureHandler(customAuthenticationFailureHandler) // Use the custom failure handler
                .and()
                .logout() // Enable logout
                .logoutUrl("/logout") // Set the URL for logout
                .logoutSuccessUrl("/") // Redirect to this URL after logout
                .invalidateHttpSession(true) // Invalidate the HTTP session
                .deleteCookies("JSESSIONID") // Delete cookies (if any);
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler); // Set the custom AccessDeniedHandler
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
