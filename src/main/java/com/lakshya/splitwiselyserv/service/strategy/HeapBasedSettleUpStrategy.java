package com.lakshya.splitwiselyserv.service.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.lakshya.splitwiselyserv.dto.TransactionDTO;
import com.lakshya.splitwiselyserv.model.Expense;
import com.lakshya.splitwiselyserv.model.User;
import com.lakshya.splitwiselyserv.model.UserExpense;
import com.lakshya.splitwiselyserv.model.constants.UserExpenseType;

public class HeapBasedSettleUpStrategy implements SettleUpStrategy {
    @Override
    public List<TransactionDTO> settleUp(List<Expense> expenses) {
        List<TransactionDTO> transactions = new ArrayList<>();
        HashMap<String, Double> outstandingHm = new HashMap<>();

        // Calculate total outstanding amount for each user;
        for (Expense expense : expenses) {
            for (var userExpense : expense.getUserExpenses()) {
                User user = userExpense.getUser();
                double amt = outstandingHm.getOrDefault(user.getName(), 0D);
                outstandingHm.put(user.getName(), getUpdatedOutstandingAmount(amt, userExpense));
            }
        }

        // store all negative outstanding amounts to minHeap & positive ones to maxHeap
        PriorityQueue<Map.Entry<String, Double>> minHeap = new PriorityQueue<>(
                Map.Entry.comparingByValue());
        PriorityQueue<Map.Entry<String, Double>> maxHeap = new PriorityQueue<>(
                (a, b) -> Double.compare(b.getValue(), a.getValue()));
        outstandingHm.forEach((userName, outstandingAmt) -> {
            if (outstandingAmt < 0)
                minHeap.offer(Map.entry(userName, outstandingAmt));
            else if (outstandingAmt > 0)
                maxHeap.offer(Map.entry(userName, outstandingAmt));
        });

        // use minHeap & maxHeap to record transactions
        while (!minHeap.isEmpty()) {
            TransactionDTO transactionDTO = generateTransaction(minHeap, maxHeap);
            transactions.add(transactionDTO);
        }

        return transactions;
    }

    private double getUpdatedOutstandingAmount(double currentAmount, UserExpense userExpense) {
        if (userExpense.getUserExpenseType().equals(UserExpenseType.HAS_PAID))
            return currentAmount + userExpense.getAmount();
        return currentAmount - userExpense.getAmount();
    }

    private TransactionDTO generateTransaction(PriorityQueue<Map.Entry<String, Double>> minHeap,
            PriorityQueue<Map.Entry<String, Double>> maxHeap) {
        var sender = minHeap.poll();
        var receiver = maxHeap.poll();

        assert sender != null;
        assert receiver != null;
        double balanceAmount = sender.getValue() + receiver.getValue();
        
        if (balanceAmount < 0) {
            sender.setValue(balanceAmount);
            minHeap.offer(sender);
        } else if (balanceAmount > 0) {
            receiver.setValue(balanceAmount);
            maxHeap.offer(receiver);
        }
        
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setFromUserName(sender.getKey());
        transactionDTO.setToUserName(receiver.getKey());
        double transactionAmt = Math.min(Math.abs(sender.getValue()),receiver.getValue());
        transactionDTO.setAmount(transactionAmt);

        return transactionDTO;
    }
}
