package com.lakshya.splitwiselyserv.service;

import com.lakshya.splitwiselyserv.model.Expense;
import com.lakshya.splitwiselyserv.model.Group;
import com.lakshya.splitwiselyserv.model.User;
import com.lakshya.splitwiselyserv.model.UserExpense;
import com.lakshya.splitwiselyserv.model.constants.Currency;
import com.lakshya.splitwiselyserv.model.constants.UserExpenseType;
import com.lakshya.splitwiselyserv.repository.ExpenseRepository;
import com.lakshya.splitwiselyserv.repository.GroupRepository;
import com.lakshya.splitwiselyserv.repository.UserExpenseRepository;
import com.lakshya.splitwiselyserv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitServiceImpl implements InitService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserExpenseRepository userExpenseRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public void initialise() {
        Group group = new Group();
        group.setName("Group 1");
        group.setDescription("Goa trip that will never happen");
        group.setDefaultCurrency(Currency.INR);
        Group savedGroup = groupRepository.save(group);

        User user1 = User.builder()
                         .name("User1")
                         .email("user1@email.com")
                         .phoneNumber("123456789")
                         .groups(List.of(savedGroup))
                         .build();
        User user2 = User.builder()
                         .name("User2")
                         .email("user2@email.com")
                         .phoneNumber("234567891")
                         .groups(List.of(savedGroup))
                         .build();
        User user3 = User.builder()
                         .name("User3")
                         .email("user3@email.com")
                         .phoneNumber("345678912")
                         .groups(List.of(savedGroup))
                         .build();
        User user4 = User.builder()
                         .name("User4")
                         .email("user4@email.com")
                         .phoneNumber("456789123")
                         .groups(List.of(savedGroup))
                         .build();
        User user5 = User.builder()
                         .name("User5")
                         .email("user5@email.com")
                         .phoneNumber("567891234")
                         .groups(List.of(savedGroup))
                         .build();
        User user6 = User.builder()
                         .name("User6")
                         .email("user6@email.com")
                         .phoneNumber("678912345")
                         .groups(List.of(savedGroup))
                         .build();

        var savedUsers = userRepository.saveAll(List.of(user1, user2, user3, user4, user5, user6));
        savedGroup.setUsers(savedUsers);
        groupRepository.save(savedGroup);

        UserExpense userExpense1 = new UserExpense();
        userExpense1.setUserExpenseType(UserExpenseType.HAS_TO_PAY);
        userExpense1.setAmount(500);
        userExpense1.setUser(savedUsers.get(0));
        UserExpense userExpense2 = new UserExpense();
        userExpense2.setUserExpenseType(UserExpenseType.HAS_TO_PAY);
        userExpense2.setAmount(2000);
        userExpense2.setUser(savedUsers.get(1));
        UserExpense userExpense3 = new UserExpense();
        userExpense3.setUserExpenseType(UserExpenseType.HAS_TO_PAY);
        userExpense3.setAmount(500);
        userExpense3.setUser(savedUsers.get(2));
        UserExpense userExpense4 = new UserExpense();
        userExpense4.setUserExpenseType(UserExpenseType.HAS_PAID);
        userExpense4.setAmount(1500);
        userExpense4.setUser(savedUsers.get(3));
        UserExpense userExpense5 = new UserExpense();
        userExpense5.setUserExpenseType(UserExpenseType.HAS_PAID);
        userExpense5.setAmount(500);
        userExpense5.setUser(savedUsers.get(4));
        UserExpense userExpense6 = new UserExpense();
        userExpense6.setUserExpenseType(UserExpenseType.HAS_PAID);
        userExpense6.setAmount(1000);
        userExpense6.setUser(savedUsers.get(5));

        List<UserExpense> savedUserExpenses = userExpenseRepository.saveAll(
                List.of(userExpense1, userExpense2, userExpense3, userExpense4, userExpense5, userExpense6));

        Expense expense = new Expense();
        expense.setDescription("Goa trip food");
        expense.setAmount(3000);
        expense.setUserExpenses(savedUserExpenses);
        expense.setCurrency(Currency.INR);
        Expense savedExpense = expenseRepository.save(expense);

        savedGroup.setExpenses(List.of(savedExpense));
        groupRepository.save(savedGroup);

    }
}
