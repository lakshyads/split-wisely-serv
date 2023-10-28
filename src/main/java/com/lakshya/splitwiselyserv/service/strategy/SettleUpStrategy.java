package com.lakshya.splitwiselyserv.service.strategy;

import java.util.List;

import com.lakshya.splitwiselyserv.dto.TransactionDTO;
import com.lakshya.splitwiselyserv.model.Expense;

public interface SettleUpStrategy {
    List<TransactionDTO> settleUp(List<Expense> expenses);
}
