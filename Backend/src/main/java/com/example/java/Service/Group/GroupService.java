package com.example.java.Service.Group;

import java.util.List;

import com.example.java.Entity.Group.Group;


public interface GroupService {


    //create a group
    Group createGroup(Group group,String userId);


    //entering to my group
    Group getMyGroup(String UserId);

    //get a group by id
    Group getGroupById(int id);

    //get all groups
    List<Group> getAllGroups();

    //delete all the group
    void deleteAllGroups();

   

    // //update a group
    // Group updateGroup(int id,Group gorup);

    

}
