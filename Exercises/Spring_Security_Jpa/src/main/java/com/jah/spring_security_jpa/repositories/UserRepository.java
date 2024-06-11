/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jah.spring_security_jpa.repositories;

import com.jah.spring_security_jpa.models.User;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author drjal
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByUserName(String userName);

    @Modifying
    @Query("UPDATE User u SET u.userName = :userName, u.active = :active WHERE u.id = :id")
    void updateUserDetails(
            @Param("id") int id,
            @Param("userName") String userName,
            @Param("active") boolean active);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user WHERE user_id = :userId", nativeQuery = true)
    void deleteUserByUserId(@Param("userId") int userId);

}
