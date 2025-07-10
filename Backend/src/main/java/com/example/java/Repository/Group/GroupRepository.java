package com.example.java.Repository.Group;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.java.Entity.Expense.Expense;
import com.example.java.Entity.Group.Group;

import jakarta.transaction.Transactional;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    //finding the group by groupCode
     Optional<Group> findByGroupCode(String groupCode);


     //deleting all the groups
     @Modifying
     @Transactional
     @Query("DELETE FROM Group")
     void  deleteAllGroups();

     






   

}
