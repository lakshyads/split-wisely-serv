package com.lakshya.splitwiselyserv.controller;

import com.lakshya.splitwiselyserv.dto.GroupExpenseDTO;
import com.lakshya.splitwiselyserv.dto.TransactionDTO;
import com.lakshya.splitwiselyserv.exception.GroupNotFoundException;
import com.lakshya.splitwiselyserv.service.GroupService;
import com.lakshya.splitwiselyserv.service.strategy.SettleUpStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @GetMapping("/expenses/{groupId}")
    public ResponseEntity<List<GroupExpenseDTO>> getGroupExpenses(
            @PathVariable("groupId") int groupId
    ) throws GroupNotFoundException {
        var expenses = groupService.getGroupExpensesByGroupId(groupId);
        System.out.println(
                "===> [GET /expenses/" + groupId + "] GroupController: getGroupExpenses: expenses: "
                        + expenses.toString());
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/settle-up/{groupId}")
    public ResponseEntity<List<TransactionDTO>> settleUpForGroup(
            @PathVariable("groupId") int groupId
    ) throws GroupNotFoundException {
        List<TransactionDTO> transactionDTOs = groupService.settleUpByGroupId(
                groupId, SettleUpStrategies.HEAP_BASED_SETTLE_UP_STRATEGY);
        return ResponseEntity.ok(transactionDTOs);
    }
}
