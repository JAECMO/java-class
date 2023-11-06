/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.classroster.controller;

import com.sg.classroster.dao.CourseDao;
import com.sg.classroster.dao.StudentDao;
import com.sg.classroster.dao.TeacherDao;
import com.sg.classroster.dto.Course;
import com.sg.classroster.dto.Student;
import com.sg.classroster.dto.Teacher;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author drjal
 */
@Controller
public class CourseController {

    @Autowired
    TeacherDao teacherDao;

    @Autowired
    StudentDao studentDao;

    @Autowired
    CourseDao courseDao;

    Set<ConstraintViolation<Course>> violations = new HashSet<>();

    @GetMapping("courses")
    public String displayCourses(Model model) {
        List<Course> courses = courseDao.getAllCourses();
        List<Teacher> teachers = teacherDao.getAllTeachers();
        List<Student> students = studentDao.getAllStudents();
        model.addAttribute("courses", courses);
        model.addAttribute("teachers", teachers);
        model.addAttribute("students", students);
        if(!violations.isEmpty()){
        violations.clear();
      
        }
        model.addAttribute("errors", violations);
        return "courses";
    }

    @PostMapping("addCourse")
    public String addCourse(Course course, Model model, HttpServletRequest request) {
        String name = request.getParameter("name");
        String teacherId = request.getParameter("teacherId");
        String[] studentIds = request.getParameterValues("studentId");

        if (teacherId != null) {
            course.setTeacher(teacherDao.getTeacherById(Integer.parseInt(teacherId)));
        }

        List<Student> students = new ArrayList<>();
        if (studentIds != null) {
            for (String studentId : studentIds) {
                students.add(studentDao.getStudentById(Integer.parseInt(studentId)));
            }
        }
        course.setName(name);
        course.setStudents(students);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(course);

        if (violations.isEmpty()) {
            courseDao.addCourse(course);
            return "redirect:/courses";
        } else {
            List<Course> courses = courseDao.getAllCourses();
            List<Teacher> teachers = teacherDao.getAllTeachers();
            List<Student> studentsAll = studentDao.getAllStudents();
            model.addAttribute("courses", courses);
            model.addAttribute("teachers", teachers);
            model.addAttribute("students", studentsAll);
            model.addAttribute("errors", violations);
            return "courses";
        }

    }

    @GetMapping("courseDetail")
    public String courseDetail(Integer id, Model model) {
        Course course = courseDao.getCourseById(id);
        model.addAttribute("course", course);
        return "courseDetail";
    }

    @GetMapping("deleteCourse")
    public String deleteCourse(Integer id) {
        courseDao.deleteCourseById(id);
        return "redirect:/courses";
    }

    @GetMapping("editCourse")
    public String editCourse(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
      
        Course course = courseDao.getCourseById(id);
        List<Student> students = studentDao.getAllStudents();
        List<Teacher> teachers = teacherDao.getAllTeachers();
        
        
        model.addAttribute("course", course);
        model.addAttribute("students", students);
        model.addAttribute("teachers", teachers);
        
          if(!violations.isEmpty()){
             violations.clear();
        }
        model.addAttribute("errors", violations);
       
        return "editCourse";
    }

    @PostMapping("editCourse")
    public String performEditCourse(Model model,HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String teacherId = request.getParameter("teacherId");
        String[] studentIds = request.getParameterValues("studentId");
        Course course = courseDao.getCourseById(id);

        if (teacherId != null) {
            course.setTeacher(teacherDao.getTeacherById(Integer.parseInt(teacherId)));
        }
        
        List<Student> students = new ArrayList<>();
        if (studentIds != null) {
            for (String studentId : studentIds) {
                students.add(studentDao.getStudentById(Integer.parseInt(studentId)));
            }
        }

        course.setName(name);
        course.setStudents(students);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(course);

         if (violations.isEmpty()) {
            courseDao.updateCourse(course);
            return "redirect:/courses";
        } else {
            List<Student> studentsAll = studentDao.getAllStudents();
            List<Teacher> teachers = teacherDao.getAllTeachers();
            model.addAttribute("course", course);
            model.addAttribute("teachers", teachers);
            model.addAttribute("students", studentsAll);
            model.addAttribute("errors", violations);
            return "editCourse";
        }

    }

}
