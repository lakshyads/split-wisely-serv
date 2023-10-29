package com.lakshya.splitwiselyserv.service;

import com.lakshya.splitwiselyserv.dto.GroupExpenseDTO;
import com.lakshya.splitwiselyserv.dto.TransactionDTO;
import com.lakshya.splitwiselyserv.exception.GroupNotFoundException;
import com.lakshya.splitwiselyserv.model.Expense;
import com.lakshya.splitwiselyserv.model.Group;
import com.lakshya.splitwiselyserv.repository.GroupRepository;
import com.lakshya.splitwiselyserv.service.strategy.SettleUpStrategies;
import com.lakshya.splitwiselyserv.service.strategy.SettleUpStrategy;
import com.lakshya.splitwiselyserv.service.strategy.SettleUpStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;


    private List<Expense> getExpensesByGroupId(int groupId) throws GroupNotFoundException {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isEmpty())
            throw new GroupNotFoundException("Group for not found with id: " + groupId);
        return group.get().getExpenses();
    }


    @Override
    public List<GroupExpenseDTO> getGroupExpensesByGroupId(int groupId) throws GroupNotFoundException {
        return getExpensesByGroupId(groupId).stream()
                                            .map(expense -> new GroupExpenseDTO(groupId, expense))
                                            .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> settleUpByGroupId(
            int groupId, SettleUpStrategies settleUpStrategy
    ) throws GroupNotFoundException {
        SettleUpStrategy strategy = SettleUpStrategyFactory.getSettleUpStrategy(settleUpStrategy);

        assert strategy != null;
        return strategy.settleUp(getExpensesByGroupId(groupId));
    }
}
