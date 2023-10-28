package com.lakshya.splitwiselyserv.model;

import java.util.List;

import com.lakshya.splitwiselyserv.model.constants.Currency;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "SPLITWISELY_EXPENSE")
public class Expense extends BaseModel {
    private double amount;
    private String description;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToMany
    private List<UserExpense> userExpenses;
}
