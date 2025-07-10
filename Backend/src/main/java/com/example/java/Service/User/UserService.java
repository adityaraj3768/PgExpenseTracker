package com.example.java.Service.User;

import java.util.List;

import com.example.java.Entity.Group.Group;
import com.example.java.Entity.User.User;

public interface UserService {

    //creating a user and saving the user to the database
    User createUser(User user);

    //adding the user to a group
    User addUserToGroup(String groupCode,String userId);

    

    
    //getting the members of a particular group
    List<User> getGroupMembers(String groupCode);


    //getting the user by id
    User getUserById(int id);

    String getGroupCodeByUserId(String userId);


    //this will delete all the user from the database
    void deleteAllUsers();

    



}
