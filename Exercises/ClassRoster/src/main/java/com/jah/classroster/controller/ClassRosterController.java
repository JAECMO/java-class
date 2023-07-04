/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.classroster.controller;

import com.jah.classroster.dao.ClassRosterPersistenceException;
import com.jah.classroster.dto.Student;
import com.jah.classroster.ui.*;
import com.sg.classroster.service.ClassRosterDataValidationException;
import com.sg.classroster.service.ClassRosterDuplicateIdException;
import com.sg.classroster.service.ClassRosterServiceLayer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *
 * @author drjal
 */

public class ClassRosterController {
    
    private ClassRosterView view;
    private ClassRosterServiceLayer service;
    
 
    public ClassRosterController(ClassRosterServiceLayer service, ClassRosterView view) {
    this.service = service;
    this.view = view;
}

   public void run() {
    boolean keepGoing = true;
    int menuSelection = 0;
    try {
        while (keepGoing) {

            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    listStudents();
                    break;
                case 2:
                    createStudent();
                    break;
                case 3:
                    viewStudent();
                    break;
                case 4:
                    removeStudent();
                    break;
                case 5:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
            }

        }
        exitMessage();
    } catch (ClassRosterPersistenceException e) {
        view.displayErrorMessage(e.getMessage());
    }
}
    
    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }
    
    private void createStudent() throws ClassRosterPersistenceException {
    view.displayCreateStudentBanner();
    boolean hasErrors = false;
    do {
        Student currentStudent = view.getNewStudentInfo();
        try {
            service.createStudent(currentStudent);
            view.displayCreateSuccessBanner();
            hasErrors = false;
        } catch (ClassRosterDuplicateIdException | ClassRosterDataValidationException e) {
            hasErrors = true;
            view.displayErrorMessage(e.getMessage());
        }
    } while (hasErrors);
}
    private void listStudents() throws ClassRosterPersistenceException {
    view.displayDisplayAllBanner();
     List<Student> studentList = service.getAllStudents();
    view.displayStudentList(studentList);
}
    private void viewStudent() throws ClassRosterPersistenceException {
    view.displayDisplayStudentBanner();
    String studentId = view.getStudentIdChoice();
     Student student = service.getStudent(studentId) ;
     view.displayStudent(student);
}
    private void removeStudent() throws ClassRosterPersistenceException {
     view.displayRemoveStudentBanner();
    String studentId = view.getStudentIdChoice();
    service.removeStudent(studentId);
    view.displayRemoveSuccessBanner();
}
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
}
