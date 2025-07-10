package com.example.java.Service.Expense;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.DTO.ExpenseRequest;
import com.example.java.Entity.Expense.Expense;
import com.example.java.Entity.Group.Group;
import com.example.java.Entity.User.User;
import com.example.java.Repository.Expense.ExpenseRepository;
import com.example.java.Repository.Group.GroupRepository;
import com.example.java.Repository.User.UserRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    GroupRepository groupRepository;

    //this method will add the expense to the particular group
    @Override
    public Expense addExpense(ExpenseRequest req,String userId) {

        //fetching the user 
        User user=userRepository.findByUserId(userId)
        .orElseThrow(()->new RuntimeException("User Not found!"));
        
        //name of payer
        String name = user.getName();

        //now fetching  the group
         Group group=user.getGroup();

         
         

         //now adding the expense to the group
         Expense expense=new Expense();

         expense.setAmount(req.getAmount());
         expense.setPaymentDate(req.getPaymentDate());
         expense.setTags(req.getTags());
         expense.setPaidBy(name);
         
         expense.setUser(user);
         expense.setGroup(group);

         return expenseRepository.save(expense);

         
      }

      //this will get the all the expenses of the group
      @Override
      public List<Expense> getAllExpenses(Group group) {
         List<Expense> expense=expenseRepository.findByGroup(group);
            
           return expense;
      }
         

      //this will get the expenses of the particular person 
      @Override
      public List<Expense> getAllIndividualExpenses(User user) {
          List<Expense> expense=expenseRepository.findByUser(user);
          
           return expense;
      }
       
      //this will all the expenses from the DB
      @Override
      public void deleteAllExpenses() {
            expenseRepository.deleteAllExpenses();
      }

    @Override
    public int totalExpense() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
      
     





    


}
