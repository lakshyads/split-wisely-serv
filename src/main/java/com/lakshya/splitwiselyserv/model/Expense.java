package com.lakshya.splitwiselyserv.model;

import java.util.List;

import com.lakshya.splitwiselyserv.model.constants.Currency;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "splitwisely_expense")
public class Expense extends BaseModel {
    private double amount;
    private String description;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToMany
    @JoinColumn(name = "splitwisely_expense_id")
    private List<UserExpense> userExpenses;
}
