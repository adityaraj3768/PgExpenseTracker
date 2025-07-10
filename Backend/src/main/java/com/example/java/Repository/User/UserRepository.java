package com.example.java.Repository.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.java.Entity.Group.Group;
import com.example.java.Entity.User.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends  JpaRepository<User, Integer> {
    // Custom query methods can be defined here if needed
    // For example, to find a user by userId:
    // User findByUserId(String userId);
    
    Optional<User> findByUserId(String userId);

    @Query("SELECT u.group.groupCode FROM User u WHERE u.userId = :userId")
    String findGroupCodeByUserId(@Param("userId") String userId);


    // //finding the list of  all the  expenses of the particular user
    // List<Expense> findByUser(User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM User")
    void deleteAllUsers();

    //this will give me  the size of the group by group code
    @Query("SELECT COUNT(u) FROM User u WHERE u.group.groupCode = :groupCode")
    int countUsersByGroupCode(@Param("groupCode") String groupCode);


    //this will give the list all user my  group
    @Query("SELECT u FROM User u WHERE u.group.groupCode = :groupCode")
    List<User> findAllByGroupCode(@Param("groupCode") String groupCode);

     
    //this will get  the user group
    @Query("SELECT u.group FROM User u WHERE u.userId = :userId")
    Group getUserGroup(@Param("userId") String userId);




    

}
