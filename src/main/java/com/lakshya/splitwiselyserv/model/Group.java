package com.lakshya.splitwiselyserv.model;

import java.util.List;

import com.lakshya.splitwiselyserv.model.constants.Currency;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "splitwisely_group")
public class Group extends BaseModel {
    private String name;
    private String description;
    private double total_amount;

    @Enumerated(EnumType.STRING)
    private Currency default_currency;

    @ManyToMany
    private List<User> users;

    @OneToMany
    @JoinColumn(name = "splitwisely_group_id")
    private List<Expense> expenses;
}
