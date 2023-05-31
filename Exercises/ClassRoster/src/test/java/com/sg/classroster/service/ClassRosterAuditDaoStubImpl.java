/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.classroster.service;

import com.jah.classroster.dao.ClassRosterAuditDao;
import com.jah.classroster.dao.ClassRosterPersistenceException;

/**
 *
 * @author drjal
 */
public class ClassRosterAuditDaoStubImpl implements ClassRosterAuditDao {

    @Override
    public void writeAuditEntry(String entry) throws ClassRosterPersistenceException {
        //do nothing . . .
    }
}
