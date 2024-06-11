/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.handler;

import com.jah.spring_security_jpa.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author drjal
 */
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean activationPending = checkActivationStatus(username, password);

        if (activationPending) {
            setDefaultFailureUrl("/login?error=true&message=Activation pending");
        } else {
            setDefaultFailureUrl("/login?error=true&message=Invalid username and/or password");
        }

        super.onAuthenticationFailure(request, response, exception);
    }

    private boolean checkActivationStatus(String username, String password) {
        return userService.isActivationPending(username, password);
    }
}
