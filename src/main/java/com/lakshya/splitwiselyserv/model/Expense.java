package com.lakshya.splitwiselyserv.model;

import com.lakshya.splitwiselyserv.model.constants.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
