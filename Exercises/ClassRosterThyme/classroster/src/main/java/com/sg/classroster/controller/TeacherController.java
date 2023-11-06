/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.classroster.controller;

import com.sg.classroster.dao.CourseDao;
import com.sg.classroster.dao.StudentDao;
import com.sg.classroster.dao.TeacherDao;
import com.sg.classroster.dto.Teacher;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.validation.Validator;//Make trouble
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author drjal
 */
@Controller
public class TeacherController {

    @Autowired
    TeacherDao teacherDao;

    @Autowired
    StudentDao studentDao;

    @Autowired
    CourseDao courseDao;
   
    Set<ConstraintViolation<Teacher>> violations = new HashSet<>();

    @GetMapping("teachers")
    public String displayTeachers(Model model) {
        
        List<Teacher> teachers = teacherDao.getAllTeachers();
        model.addAttribute("teachers", teachers);
        if(!violations.isEmpty()){
        violations.clear();
      
        }
        model.addAttribute("errors", violations);
        return "teachers";  
        
    }

    @PostMapping("addTeacher")
    public String addTeacher(Teacher teacher,Model model,HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String specialty = request.getParameter("specialty");
     
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(teacher);

        if (violations.isEmpty()) {
            teacher.setFirstName(firstName);
            teacher.setLastName(lastName);
            teacher.setSpecialty(specialty);
            teacherDao.addTeacher(teacher);
         
            return "redirect:/teachers";
        } else {
            List<Teacher> teachers = teacherDao.getAllTeachers();
            model.addAttribute("teachers", teachers);
//            model.addAttribute("firstNameTemp", firstName);
//            model.addAttribute("lastNameTemp", lastName);
//            model.addAttribute("specialtyTemp", specialty);
            model.addAttribute("errors", violations);
            return "teachers";

        }
            
          
    }

    @GetMapping("deleteTeacher")
    public String deleteTeacher(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        teacherDao.deleteTeacherById(id);

        return "redirect:/teachers";
    }

    @GetMapping("editTeacher")
    public String editTeacher(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Teacher teacher = teacherDao.getTeacherById(id);

        model.addAttribute("teacher", teacher);
        model.addAttribute("firstName", teacher.getFirstName());
        model.addAttribute("lastName", teacher.getLastName());
        model.addAttribute("specialty", teacher.getSpecialty());

         if(!violations.isEmpty()){
             violations.clear();
        }
        model.addAttribute("errors", violations);
        
        return "editTeacher";
    }

    @PostMapping("editTeacher")
    public String performEditTeacher(Model model,HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Teacher teacher = teacherDao.getTeacherById(id);
        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String specialty = request.getParameter("specialty");
        
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setSpecialty(specialty);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(teacher);

        if (violations.isEmpty()) {
            
            teacherDao.updateTeacher(teacher);
            return "redirect:/teachers";
        } else {
            model.addAttribute("teacher", teacher);
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
            model.addAttribute("specialty", specialty);
            model.addAttribute("errors", violations);
            return "editTeacher";

        }

    }
}
