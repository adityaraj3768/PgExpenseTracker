package com.example.java.Service.User;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java.Entity.Group.Group;
import com.example.java.Entity.User.User;
import com.example.java.Repository.Group.GroupRepository;
import com.example.java.Repository.User.UserRepository;
 

@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;
    
    @Override
    public User createUser(User user) {
        //this will save the user to the 
        
        //check if the user alredy exists
        if(userRepository.existsById(user.getId())){
            throw new RuntimeException("User already exists with id: "+user.getId());
        }
        //if not then save the user
        return userRepository.save(user);


    }
    

    //to  to join group
    @Override
    public User addUserToGroup(String groupCode, String userId) {
        //check if the user exists
          User existingUser = userRepository.findByUserId(userId)
          .orElseThrow(() -> new RuntimeException("User not found"));

          //check if the user is already in the group
            //get the group id from the user
            if(existingUser.getGroup()!=null){
                throw new IllegalStateException("User is already in a group");
            }
            

            //now fetch the group by groupCode
            Group group = groupRepository.findByGroupCode(groupCode)
            .orElseThrow(()-> new RuntimeException("Group not found with code: "+groupCode));

            //set the group to the user
            existingUser.setGroup(group);

            group.getUsers().add(existingUser);

            //save the user to the database
            userRepository.save(existingUser);

            return existingUser;
        }
        
        
            @Override
            public List<User> getGroupMembers(String groupCode) {
                 
                 return null;
            }
        
            @Override
            public User getUserById(int id) {
                 return null;
            }

    @Override
    public String getGroupCodeByUserId(String userId) {
        return  userRepository.findGroupCodeByUserId(userId);
    }

    


    @Override
    public void deleteAllUsers() {
          userRepository.deleteAllUsers();
    }

    
        
        }






         

         





         

        
