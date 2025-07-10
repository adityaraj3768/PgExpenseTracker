package com.example.java.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserExpenseRaw {
    private String userId;
    private BigDecimal total;

}
