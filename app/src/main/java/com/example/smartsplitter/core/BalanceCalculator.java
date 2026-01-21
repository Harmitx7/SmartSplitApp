package com.example.smartsplitter.core;

import com.example.smartsplitter.data.Expense;
import com.example.smartsplitter.data.ExpenseSplit;
import com.example.smartsplitter.data.ExpenseWithSplits;
import com.example.smartsplitter.data.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BalanceCalculator {

    public static class MemberBalance {
        public String memberId;
        public String displayName;
        public double totalPaid;
        public double totalOwed;
        public double netBalance;

        public MemberBalance(String memberId) {
            this.memberId = memberId;
            this.totalPaid = 0;
            this.totalOwed = 0;
            this.netBalance = 0;
        }
    }

    public Map<String, MemberBalance> calculateBalances(List<ExpenseWithSplits> expenses, List<Member> members) {
        Map<String, MemberBalance> balances = new HashMap<>();

        // Initialize balances for all members
        for (Member member : members) {
            balances.put(member.memberId, new MemberBalance(member.memberId));
            // We'll need member names later, so storing them in the balance object is good practice
            // Ideally we'd map this better, but assuming Member ID is key
            if (balances.get(member.memberId) != null) {
                balances.get(member.memberId).displayName = member.displayName;
            }
        }

        for (ExpenseWithSplits expenseWithSplits : expenses) {
            Expense expense = expenseWithSplits.expense;
            
            // Skip if it's a settlement? 
            // The PRD says "Settlement creates two offsetting transactions" or similar. 
            // If isSettlement is true, it might be handled differently or just treated as a payment.
            // If it's a settlement expense, it means Payer paid Receiver. Payer gets credit, Receiver gets debit.
            // Logic should be same: Payer Paid X. Receiver (in split) owes X.
            // So net effect: Payer +X, Receiver -X.
            // If Payer owed Receiver X before, Payer (-X) + X = 0. Receiver (+X) - X = 0.
            // So standard logic works for settlements too if modeled correctly.

            MemberBalance payer = balances.get(expense.payerMemberId);
            if (payer == null) {
                // Should not happen if data integrity is maintained
                payer = new MemberBalance(expense.payerMemberId);
                balances.put(expense.payerMemberId, payer);
            }
            payer.totalPaid += expense.totalAmount;

            for (ExpenseSplit split : expenseWithSplits.splits) {
                MemberBalance participant = balances.get(split.memberId);
                if (participant == null) {
                    participant = new MemberBalance(split.memberId);
                    balances.put(split.memberId, participant);
                }
                participant.totalOwed += split.amount;
            }
        }

        // Calculate Net
        for (MemberBalance b : balances.values()) {
            b.netBalance = b.totalPaid - b.totalOwed;
        }

        return balances;
    }
}
