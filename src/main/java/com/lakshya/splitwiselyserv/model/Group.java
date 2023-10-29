package com.lakshya.splitwiselyserv.model;

import com.lakshya.splitwiselyserv.model.constants.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "splitwisely_group")
public class Group extends BaseModel {
    private String name;
    private String description;
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    private Currency defaultCurrency;

    @ManyToMany
    private List<User> users;

    @OneToMany
    @JoinColumn(name = "splitwisely_group_id")
    private List<Expense> expenses;
}
