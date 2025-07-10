package com.example.java.Service.Expense;

import java.util.List;

import com.example.java.DTO.ExpenseRequest;
import com.example.java.Entity.Expense.Expense;
import com.example.java.Entity.Group.Group;
import com.example.java.Entity.User.User;

public interface ExpenseService {
    //all the expensen service will be defined here

    //adding the expense to the group
     Expense addExpense(ExpenseRequest req, String userId);

     //view all the expenses
      List<Expense> getAllExpenses(Group group);

     //view the individual expenses
     List<Expense> getAllIndividualExpenses(User user);
     
     //this will delete all the expenses
     void deleteAllExpenses();

     //total expense of group
     int totalExpense();






}
