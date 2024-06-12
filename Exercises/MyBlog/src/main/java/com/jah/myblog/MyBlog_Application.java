/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.myblog;

import com.jah.myblog.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author drjal
 */
@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class MyBlog_Application {
    public static void main(String[] args) {
		SpringApplication.run(MyBlog_Application.class, args);
                
                String rawPassword = "pass";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println("Encoded Password: " + encodedPassword);
	}
}
