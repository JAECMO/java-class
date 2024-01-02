/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.classroster.controller;

import com.sg.classroster.dao.CourseDao;
import com.sg.classroster.dao.StudentDao;
import com.sg.classroster.dao.TeacherDao;
import com.sg.classroster.dto.Student;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author drjal
 */
@Controller
public class StudentController {

    @Autowired
    TeacherDao teacherDao;

    @Autowired
    StudentDao studentDao;

    @Autowired
    CourseDao courseDao;
    
    Set<ConstraintViolation<Student>> violations = new HashSet<>();

    @GetMapping("students")
    public String displayStudents(Model model) {
        List<Student> students = studentDao.getAllStudents();
        model.addAttribute("students", students);
        if (!violations.isEmpty()) {
            violations.clear();

        }
        model.addAttribute("errors", violations);
        return "students";
    }

    @PostMapping("addStudent")
    public String addStudent(Student student,Model model,HttpServletRequest request) {
        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
     
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(student);
      
        if (violations.isEmpty()) {
            student.setFirstName(firstName);
            student.setLastName(lastName);
            studentDao.addStudent(student);
            return "redirect:/students";
        } else {
            List<Student> students = studentDao.getAllStudents();
            model.addAttribute("students", students);
            model.addAttribute("errors", violations);
            return "students";
        }
        
    }

    @GetMapping("deleteStudent")
    public String deleteStudent(Integer id) {
        studentDao.deleteStudentById(id);
        return "redirect:/students";
    }

    @GetMapping("editStudent")
    public String editStudent(Integer id, Model model) {
       
        Student student = studentDao.getStudentById(id);
        
        model.addAttribute("student", student);
        model.addAttribute("firstName", student.getFirstName());
        model.addAttribute("lastName", student.getLastName());
        
        if(!violations.isEmpty()){
             violations.clear();
        }
        model.addAttribute("errors", violations);
        
        return "editStudent";
    }

//    @PostMapping("editStudent")
//    public String performEditStudent(Student student) {
//        studentDao.updateStudent(student);
//        return "redirect:/students";
//    }  //Before inputvalidation
    
    @PostMapping("editStudent")
    public String performEditStudent(Model model, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentDao.getStudentById(id);

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        student.setFirstName(firstName);
        student.setLastName(lastName);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(student);

        if (violations.isEmpty()) {
            studentDao.updateStudent(student);
            return "redirect:/students";
        } else {
            model.addAttribute("student", student);
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
            model.addAttribute("errors", violations);
            return "editStudent";

        }
    }

}
