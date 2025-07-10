package com.example.java.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserExpenseRawDto {
    private String name;
    private BigDecimal totalSpent;

}
