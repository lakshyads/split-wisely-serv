package com.lakshya.splitwiselyserv.model;

import com.lakshya.splitwiselyserv.model.constants.UserExpenseType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "SPLITWISELY_USEREXPENSE")
public class UserExpense extends BaseModel {

    @ManyToOne
    private User user;
    private double amount;
    @Enumerated(EnumType.STRING)
    private UserExpenseType userExpenseType;

}
