package com.lakshya.splitwiselyserv.dto;

import com.lakshya.splitwiselyserv.model.Expense;
import com.lakshya.splitwiselyserv.model.constants.UserExpenseType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupExpenseDTO {
    private int groupId;
    private String description;
    private double amount;
    private List<UserExpenseDTO> paidByList;
    private List<UserExpenseDTO> expenseShareList;

    public GroupExpenseDTO(int groupId, Expense expense) {
        this.groupId = groupId;
        this.description = expense.getDescription();
        this.amount = expense.getAmount();
        this.paidByList = new ArrayList<>();
        this.expenseShareList = new ArrayList<>();

        for (var userExpense : expense.getUserExpenses()) {
            int userId = userExpense.getUser().getId();
            String userName = userExpense.getUser().getName();
            double amount = userExpense.getAmount();
            var userExpenseDTO = new UserExpenseDTO(amount, userId, userName);

            if (userExpense.getUserExpenseType().equals(UserExpenseType.HAS_PAID))
                this.paidByList.add(userExpenseDTO);
            else
                this.expenseShareList.add(userExpenseDTO);
        }
    }
}
