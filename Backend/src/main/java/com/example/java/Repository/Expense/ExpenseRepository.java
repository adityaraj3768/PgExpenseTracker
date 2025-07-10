package com.example.java.Repository.Expense;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.java.DTO.UserExpenseRaw;
import com.example.java.Entity.Expense.Expense;
import com.example.java.Entity.Group.Group;
import com.example.java.Entity.User.User;

import jakarta.transaction.Transactional;

public interface ExpenseRepository extends JpaRepository<Expense, Integer>{

    //all the custom query will be here 
    List<Expense> findByUser(User user);

    //finding all the expenses that the use have
     List<Expense> findByGroup(Group group);
     
     @Modifying
     @Transactional
     @Query("DELETE FROM Expense")
     void deleteAllExpenses();


      //this will give the total expense of your group
      @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.group.groupCode = :groupCode")
       BigDecimal getTotalExpenseByGroupCode(@Param("groupCode") String groupCode);


       //this will give the all the expenses of   the particular group
       @Query("SELECT e FROM Expense e WHERE e.group.groupCode = :groupCode ORDER BY e.paymentDate DESC")
       List<Expense> findAllByGroupCode(@Param("groupCode") String groupCode);



       //this will give the total money spent by the user
       @Query("SELECT new com.example.java.DTO.UserExpenseRaw(e.user.userId, SUM(e.amount)) " +
       "FROM Expense e WHERE e.group.groupCode = :groupCode GROUP BY e.user.userId")
       List<UserExpenseRaw> getUserTotalsByGroup(@Param("groupCode") String groupCode);





}
