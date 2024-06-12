/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.myblog.handler;

/**
 *
 * @author drjal
 */

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest request, Exception e, Model model) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", e.getClass().getSimpleName());
        mav.addObject("message", e.getMessage());
        mav.addObject("timestamp", new Date());
        mav.addObject("status", 500);
        mav.setViewName("error");
        return mav;
    }
}

