/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.testing.dao.implementations;

import com.sg.testing.dao.MonsterDao;
import com.sg.testing.model.Monster;
import com.sg.testing.model.MonsterType;
import java.io.FileWriter;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author drjal
 */
public class AGoodMonsterDaoTest {

    private AGoodMonsterDao testDao;

    @BeforeEach
    public void setup() {
        testDao = new AGoodMonsterDao();
    }

    @Test
    public void testAddGetMonster() throws Exception {

        Monster monster = new Monster("Monster1", MonsterType.YETI, 3, "Pizza");
        int monsterId = 1;

        //  Add the monster to the DAO
        //testDao.monsters.put(monsterId, monster);
        testDao.addMonster(monsterId, monster);
        // Get the monster from the DAO
        Monster retrievedMonster = testDao.getMonster(monsterId);

        // Check the data is equal
        assertEquals(monster.getName(),
                retrievedMonster.getName(),
                "Checking monster name.");
        assertEquals(monster.getFavoriteFood(),
                retrievedMonster.getFavoriteFood(),
                "Checking monster favorite food.");
        assertEquals(monster.getPeopleEaten(),
                retrievedMonster.getPeopleEaten(),
                "Checking nb of people eaten by monster.");
        assertEquals(monster.getType(),
                retrievedMonster.getType(),
                "Checking monster type.");
    }

    @Test
    public void testAddGetAllMonsters() throws Exception {

        Monster monster1 = new Monster("Monster1", MonsterType.YETI, 3, "Pizza");
        Monster monster2 = new Monster("Monster2", MonsterType.LIZARDMAN, 5, "Burger");

        int monster1Id = 1;
        int monster2Id = 2;

        // Add both our monsers to the DAO
        testDao.addMonster(monster1Id, monster1);
        testDao.addMonster(monster2Id, monster2);

        // Retrieve the list of all monsters within the DAO
        List<Monster> allMonsters = testDao.getAllMonsters();

        // First check the general contents of the list
        assertNotNull(allMonsters, "The list of monsters must not be null");
        assertEquals(2, allMonsters.size(), "List of monsters should have 2 monsters.");

        // Then the specifics
        assertTrue(allMonsters.contains(monster1),
                "The list of monsters should include Monster1.");
        assertTrue(allMonsters.contains(monster2),
                "The list of monsters should include Monster2.");

    }
    
    
    @Test
    public void testUpdateMonster() {
        // Create a new Monster and add it to the DAO
        Monster monster = new Monster("Monster1", MonsterType.YETI, 3, "Pizza");
        int monsterId = 1;
        testDao.addMonster(monsterId, monster);

        // Create an updated version of the Monster
        Monster updatedMonster = new Monster("Monster1", MonsterType.LIZARDMAN, 5, "Burger");

        // Update the Monster in the DAO
        testDao.updateMonster(monsterId, updatedMonster);

        // Retrieve the Monster from the DAO
        Monster retrievedMonster = testDao.getMonster(monsterId);

        // Verify that the retrieved Monster matches the updated Monster
       assertEquals(updatedMonster, retrievedMonster);
    }
    
    @Test
public void testRemoveMonster() throws Exception {

        Monster monster1 = new Monster("Monster1", MonsterType.YETI, 3, "Pizza");
        Monster monster2 = new Monster("Monster2", MonsterType.LIZARDMAN, 5, "Burger");

        int monster1Id = 1;
        int monster2Id = 2;

        // Add both our monsers to the DAO
        testDao.addMonster(monster1Id, monster1);
        testDao.addMonster(monster2Id, monster2);

    // remove the first monster
    Monster removedMonster = testDao.removeMonster(monster1Id);

    // Check that the correct object was removed.
    assertEquals(removedMonster, monster1, "The removed monster should be Monster1.");

    // Get all the monsters
    List<Monster> allMonsters = testDao.getAllMonsters();

    // First check the general contents of the list
    assertNotNull( allMonsters, "All monsters list should be not null.");
    assertEquals( 1, allMonsters.size(), "All students should only have 1 monster.");

    // Then the specifics
    assertFalse( allMonsters.contains(monster1), "All monsters should NOT include monster1.");
    assertTrue( allMonsters.contains(monster2), "All monsters should NOT include monster2.");    

    // Remove the second monster
    removedMonster = testDao.removeMonster(monster2Id);
    // Check that the correct object was removed.
    assertEquals( removedMonster, monster2, "The removed  monster should be monster2.");

    // retrieve all of the monsters again, and check the list.
    allMonsters = testDao.getAllMonsters();

    // Check the contents of the list - it should be empty
    assertTrue( allMonsters.isEmpty(), "The retrieved list of monsters should be empty.");

    // Try to 'get' both students by their old id - they should be null!
    Monster retrievedMonster = testDao.getMonster(monster1Id);
    assertNull(retrievedMonster, "Monster1 was removed, should be null.");

    retrievedMonster = testDao.getMonster(monster2Id);
    assertNull(retrievedMonster, "Monster2 was removed, should be null.");

}

}
