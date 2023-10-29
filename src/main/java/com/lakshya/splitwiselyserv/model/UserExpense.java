package com.lakshya.splitwiselyserv.model;

import com.lakshya.splitwiselyserv.model.constants.UserExpenseType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "splitwisely_user_expense")
public class UserExpense extends BaseModel {
    private double amount;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private UserExpenseType userExpenseType;

}
