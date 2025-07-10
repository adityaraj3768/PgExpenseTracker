package com.example.java.Entity.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.example.java.Entity.Group.Group;
import com.example.java.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="expense")
public class Expense {
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private int id;

   @Column(precision = 10, scale = 2, nullable = false)
   private BigDecimal amount;

   @CreationTimestamp
   @Column(nullable=false)
   private LocalDate paymentDate;
   
   @ElementCollection
   @CollectionTable(name = "expense_tags", joinColumns = @JoinColumn(name = "expense_id"))
   @Column(name = "tag", nullable = false)
   private List<String> tags;

   private String paidBy;


   
   @ManyToOne
   @JoinColumn(name="user_id",nullable=false)
   @JsonBackReference
   private User user;
   
   @ManyToOne
   @JoinColumn(name="group_id",nullable=false)
   @JsonIgnore
   private Group  group;




}
