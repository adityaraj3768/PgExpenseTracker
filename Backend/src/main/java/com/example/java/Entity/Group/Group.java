
package com.example.java.Entity.Group;

import java.util.List;

import com.example.java.Entity.Expense.Expense;
import com.example.java.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name="user_group")
public class Group {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(unique=true)
    private String groupCode;
    
    private String groupName;

    //a group will have the list of  user because  one  group will have  can have more  than one user
    @OneToMany(mappedBy="group" ,fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    
    private List<User> users;
    
    //a group will have a list of expenses because the one group many expenses
    @OneToMany(mappedBy="group")
    
    private List<Expense> expenses;
    
    



}
