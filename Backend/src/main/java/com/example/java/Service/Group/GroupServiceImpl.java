package com.example.java.Service.Group;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.java.Entity.Group.Group;
import com.example.java.Entity.User.User;
import com.example.java.Repository.Group.GroupRepository;
import com.example.java.Repository.User.UserRepository;

@Service
public class GroupServiceImpl implements GroupService {
    
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserRepository userRepository;
    
    //this will crate a group
    //it will set the  group name and generate a unique group code
    @Override
    public Group createGroup(Group group,String userId) {
        //fetching the useform DB

            User user = userRepository.findByUserId(userId)
        .orElseThrow(() -> new UsernameNotFoundException("User does not exist !"));

        //Check if user already belongs to a group
       if (user.getGroup() != null) {
        throw new IllegalStateException("User already belongs to a group");
          }

          group.setGroupName(group.getGroupName());
          group.setGroupCode(generateGroupCode());
           
          //Save the group
         Group savedGroup = groupRepository.save(group);

        //Assign user to the new group
        user.setGroup(savedGroup);
        userRepository.save(user); // update user

       return  groupRepository.findById(savedGroup.getId())
        .orElseThrow(() -> new RuntimeException("Group not found after saving"));
    }


    //this will generate a unique group code
    private String generateGroupCode() {
    return "PG" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        
}

  
    //this will get my group
    @Override
    public Group getMyGroup(String userId) {
        return   userRepository.getUserGroup(userId);
    }
       
   

  
    @Override
    public Group getGroupById(int id) {
        //check if the group exists or not
        Group group=groupRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Group not found with id: "+id));

        return group;
        
        
    }

    @Override
    public List<Group> getAllGroups() {
        //fetch all the  groups from the database
        List<Group> groups=groupRepository.findAll();
        if(groups.isEmpty()){
            throw new RuntimeException("No groups found");
        }
         
        return groups;
       
    }
    
    //deleting all the groups
    @Override
    public void deleteAllGroups() {
        groupRepository.deleteAllGroups();
        
    }

    

}
