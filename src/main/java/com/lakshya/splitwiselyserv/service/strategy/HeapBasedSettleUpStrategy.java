package com.lakshya.splitwiselyserv.service.strategy;

import com.lakshya.splitwiselyserv.dto.TransactionDTO;
import com.lakshya.splitwiselyserv.model.Expense;
import com.lakshya.splitwiselyserv.model.User;
import com.lakshya.splitwiselyserv.model.UserExpense;
import com.lakshya.splitwiselyserv.model.constants.UserExpenseType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class HeapBasedSettleUpStrategy implements SettleUpStrategy {
    @Override
    public List<TransactionDTO> settleUp(List<Expense> expenses) {
        List<TransactionDTO> transactions = new ArrayList<>();
        HashMap<User, Double> outstandingHm = new HashMap<>();

        // Calculate total outstanding amount for each user;
        for (Expense expense : expenses) {
            for (var userExpense : expense.getUserExpenses()) {
                User user = userExpense.getUser();
                double amt = outstandingHm.getOrDefault(user, 0D);
                outstandingHm.put(user, getUpdatedOutstandingAmount(amt, userExpense));
            }
        }

        // store all negative outstanding amounts to minHeap & positive ones to maxHeap
        PriorityQueue<Entry<User, Double>> minHeap = new PriorityQueue<>(
                Map.Entry.comparingByValue());
        PriorityQueue<Entry<User, Double>> maxHeap = new PriorityQueue<>(
                (a, b) -> Double.compare(b.getValue(), a.getValue()));
        for (var entry : outstandingHm.entrySet())
            if (entry.getValue() < 0)
                minHeap.offer(entry);
            else if (entry.getValue() > 0)
                maxHeap.offer(entry);


        // use minHeap & maxHeap to record transactions
        while (!minHeap.isEmpty())
            transactions.add(generateTransaction(minHeap, maxHeap));

        return transactions;
    }

    private double getUpdatedOutstandingAmount(double currentAmount, UserExpense userExpense) {
        if (userExpense.getUserExpenseType()
                       .equals(UserExpenseType.HAS_PAID))
            return currentAmount + userExpense.getAmount();
        return currentAmount - userExpense.getAmount();
    }

    private TransactionDTO generateTransaction(
            PriorityQueue<Entry<User, Double>> minHeap,
            PriorityQueue<Entry<User, Double>> maxHeap
    ) {
        Map.Entry<User, Double> maxHasToPay = minHeap.poll();
        Map.Entry<User, Double> minHasToReceive = maxHeap.poll();

        assert maxHasToPay != null;
        assert minHasToReceive != null;
        double balanceAmount = maxHasToPay.getValue() + minHasToReceive.getValue();

        if (balanceAmount < 0) {
            maxHasToPay.setValue(balanceAmount);
            minHeap.offer(maxHasToPay);
        } else if (balanceAmount > 0) {
            minHasToReceive.setValue(balanceAmount);
            maxHeap.offer(minHasToReceive);
        }

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setFromUserName(maxHasToPay.getKey().getName());
        transactionDTO.setToUserName(minHasToReceive.getKey().getName());
        double transactionAmt = Math.min(Math.abs(maxHasToPay.getValue()), minHasToReceive.getValue());
        transactionDTO.setAmount(transactionAmt);

        return transactionDTO;
    }
}
