package com.lakshya.splitwiselyserv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserExpenseDTO {
    private double amount;
    private int userId;
    private String userName;
}
