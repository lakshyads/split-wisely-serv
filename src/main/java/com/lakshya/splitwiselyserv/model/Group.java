package com.lakshya.splitwiselyserv.model;

import java.util.List;

import com.lakshya.splitwiselyserv.model.constants.Currency;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "SPLITWISELY_GROUP")
public class Group extends BaseModel {
    private String name;
    @Enumerated(EnumType.STRING)
    private Currency defaultCurrency;
    private double totalAmount;
    @ManyToMany(mappedBy = "groups")
    private List<User> users;
    @OneToMany
    private List<Expense> expenses;
}
