package com.lakshya.splitwiselyserv.service;

import com.lakshya.splitwiselyserv.dto.GroupExpenseDTO;
import com.lakshya.splitwiselyserv.dto.TransactionDTO;
import com.lakshya.splitwiselyserv.exception.GroupNotFoundException;
import com.lakshya.splitwiselyserv.service.strategy.SettleUpStrategies;

import java.util.List;

public interface GroupService {
    List<GroupExpenseDTO> getGroupExpensesByGroupId(int groupId) throws GroupNotFoundException;

    /**
     * Get list of transactions for settling up the group expenses
     *
     * @param groupId
     * @param settleUpStrategy
     * @return
     * @throws GroupNotFoundException
     */
    List<TransactionDTO> settleUpByGroupId(
            int groupId, SettleUpStrategies settleUpStrategy
    ) throws GroupNotFoundException;
}
