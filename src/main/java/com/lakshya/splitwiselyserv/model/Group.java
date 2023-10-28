package com.lakshya.splitwiselyserv.model;

import com.lakshya.splitwiselyserv.model.constants.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "SPLITWISELY_GROUP")
public class Group extends BaseModel {
    private String name;
    @Enumerated(EnumType.STRING)
    private Currency defaultCurrency;
    private double totalAmount;
    @ManyToMany
    private List<User> users;
    @OneToMany
    private List<Expense> expenses;
}
