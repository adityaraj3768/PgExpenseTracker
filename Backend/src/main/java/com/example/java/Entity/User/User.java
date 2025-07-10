package com.example.java.Entity.User;



import java.util.List;

import com.example.java.Entity.Expense.Expense;
import com.example.java.Entity.Group.Group;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @Column(unique=true)
    private String userId;

    private String name;
    
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="group_id")
    @JsonIgnoreProperties("users")
    private Group group;

    //one user will have the many expenses because he can buy many things 
    @OneToMany(mappedBy="user")
    
    private List<Expense> expenses;


}
