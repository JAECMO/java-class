/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.hero.dao;

import com.jah.hero.dto.SuperPower;
import java.util.List;

/**
 *
 * @author drjal
 */
public interface SuperPowerDao {

    SuperPower getSuperPowerById(int id);
    
    List<SuperPower> getAllSuperPower();

    SuperPower addSuperPower(SuperPower superPower);

    void updateSuperPower(SuperPower superPower);

    void deletesuperPower(int id);
}
