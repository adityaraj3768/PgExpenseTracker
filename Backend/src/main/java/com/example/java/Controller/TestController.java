package com.example.java.Controller;


import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.java.DTO.ExpenseRequest;
import com.example.java.DTO.JoinGroupRequest;
import com.example.java.DTO.UserExpenseRaw;
import com.example.java.DTO.UserExpenseRawDto;
import com.example.java.Entity.Expense.Expense;
import com.example.java.Entity.Group.Group;
import com.example.java.Entity.User.User;
import com.example.java.Repository.Expense.ExpenseRepository;
import com.example.java.Repository.User.UserRepository;
import com.example.java.Service.Expense.ExpenseService;
import com.example.java.Service.Group.GroupService;
import com.example.java.Service.User.UserService;


@RestController
@RequestMapping("/pg")
@CrossOrigin(origins = {
    "https://lemon-desert-0b879aa10.1.azurestaticapps.net",
    "http://localhost:3000",
    "http://localhost:5173"
}, allowCredentials = "true")
public class TestController {

   
    @Autowired
    UserService userService;
    @Autowired
    GroupService groupService;
    @Autowired
    ExpenseService expenseService;
    
    @Autowired
    UserRepository  userRepository;
    
    @Autowired
    ExpenseRepository expenseRepository;

    

    //this is a test endpoint to check if the application is running 
    @GetMapping("/test")
    public String test(){
        return "Hello World!";
    }
  


    

    //creating a group
   @PostMapping("/create-group")
    public ResponseEntity<?> createGroup(@RequestBody Group group, Principal principal) {
    System.out.println("Request for creating group");

    String userId = principal.getName(); // Extract userId from JWT token

    try {
        Group createdGroup = groupService.createGroup(group, userId);
        return ResponseEntity.ok(createdGroup);
    } catch (IllegalStateException e) {
        // User is already in a group
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (Exception e) {
        // Handle unexpected errors
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
    }
}

    

    //saving the user to a group  or joining a group
    @PostMapping("/join-group")
    public ResponseEntity<?> addUserToGroup(@RequestBody JoinGroupRequest joinGroupRequest) {
    try {
        User user = userService.addUserToGroup(joinGroupRequest.getGroupCode(), joinGroupRequest.getUserId());
        return ResponseEntity.ok(user);
    } catch (IllegalStateException e) {
        // Return 409 Conflict with message
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}



    //this will get my group
    @GetMapping("/my-group")
    public ResponseEntity<Group> getMyGroup(Principal principal){
        String userId=principal.getName();
      Group group =groupService.getMyGroup(userId);
        return ResponseEntity.ok(group);
    }


    //getting the list of all groups
    @GetMapping("/allGroups")
    public ResponseEntity<List<Group>> groups(){
     List<Group> groups=  groupService.getAllGroups();
     return ResponseEntity.ok(groups);
      }



     //get the GroupCode by the userId
     @GetMapping("/groupCode")
     public ResponseEntity<String> getGroupCodeByUserId(@RequestParam String userId){
          String groupCode=userService.getGroupCodeByUserId(userId);
          System.out.println("The GroupCode is "+groupCode);
           return ResponseEntity.ok(groupCode);
     }





     //this will add the expenses of  to the group
     @PostMapping("/addExpense")
     public ResponseEntity<Expense>  addExpense(@RequestBody ExpenseRequest req,Principal principal){
      System.out.println("The received Amount: "+ req.getAmount());

      String userId= principal.getName();
      Expense expense=expenseService.addExpense(req,userId);

      return ResponseEntity.ok(expense);

   }


   //to delete the all the expenses
   @DeleteMapping("/delete/expenses")
   public ResponseEntity<String> deleteAllExpenses(){
        expenseService.deleteAllExpenses();
        return ResponseEntity.ok("All the expenses are deleted successfully!");
   }


   //to delete the all the users
   @DeleteMapping("/delete/users")
   public ResponseEntity<String> deleteAllUsers(){
       System.out.println("request for deleting the users");
       userService.deleteAllUsers();
       return ResponseEntity.ok("All the user are deleted successfully!");

   }


   //to deleting all the users
   @DeleteMapping("/delete/groups")
   public ResponseEntity<String> deleteAllGroups(){
          groupService.deleteAllGroups();
          return ResponseEntity.ok("All the groups are deleted successfully!");
   }


   //this will give the size of the group
   @GetMapping("/group-size")
    public ResponseEntity<Integer> getMyGroupSize(Principal principal) {
    // Get logged-in userId (username)
    String userId = principal.getName();

    // Fetch the user from DB
    User user = userRepository.findByUserId(userId)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // Get the groupCode from the user's group
    String groupCode = user.getGroup().getGroupCode();

    // Use existing query method to get group size
    int size = userRepository.countUsersByGroupCode(groupCode);

    return ResponseEntity.ok(size);
}


  //total expense of the group
  @GetMapping("/totalExpense")
  public ResponseEntity<BigDecimal> getTotalExpenseOfMyGroup(Principal principal) {
    // Get userId from Principal
    String userId = principal.getName();

    // Fetch the user
    User user = userRepository.findByUserId(userId)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // Get group code or ID
    String groupCode = user.getGroup().getGroupCode(); // or use getId()

    // Calculate total expense using custom query
    BigDecimal total = expenseRepository.getTotalExpenseByGroupCode(groupCode);
    if (total == null) total = BigDecimal.ZERO; // in case group has no expenses yet

    return ResponseEntity.ok(total);
}



//this will give the list of the expenses
@GetMapping("/group/expenses")
public ResponseEntity<List<Expense>> getMyGroupExpenses(Principal principal) {
    String userId = principal.getName();
    User user = userRepository.findByUserId(userId)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    String groupCode = user.getGroup().getGroupCode();

    List<Expense> expenses = expenseRepository.findAllByGroupCode(groupCode);
    return ResponseEntity.ok(expenses);
}


  //list my all the user of my group
  @GetMapping("/group/users")
  public ResponseEntity<List<User>> getUsersInMyGroup(Principal principal) {
    // Step 1: Get userId from logged-in principal
    String userId = principal.getName();

    // Step 2: Fetch user
    User user = userRepository.findByUserId(userId)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // Step 3: Get group code or group id
    String groupCode = user.getGroup().getGroupCode(); // or use getId()

    // Step 4: Fetch users in group
    List<User> users = userRepository.findAllByGroupCode(groupCode);

    // Step 5: Return the list
    return ResponseEntity.ok(users);
}


//how much the each user have spent
@GetMapping("/group/eachExpense")
public ResponseEntity<List<UserExpenseRawDto>> getSimpleUserExpenses(Principal principal) {
    String userId = principal.getName();

    User currentUser = userRepository.findByUserId(userId)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    String groupCode = currentUser.getGroup().getGroupCode();

    List<User> users = userRepository.findAllByGroupCode(groupCode);
    List<UserExpenseRaw> totals = expenseRepository.getUserTotalsByGroup(groupCode);

    Map<String, BigDecimal> userToTotal = totals.stream()
        .collect(Collectors.toMap(UserExpenseRaw::getUserId, UserExpenseRaw::getTotal));

    List<UserExpenseRawDto> result = users.stream().map(u ->
        new UserExpenseRawDto(
            u.getName(),
            userToTotal.getOrDefault(u.getUserId(), BigDecimal.ZERO)
        )
    ).collect(Collectors.toList());

    return ResponseEntity.ok(result);
}

}









           


        
   




        

    

