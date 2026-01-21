package com.example.smartsplitter.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DebtSimplifier {

    public static class Settlement {
        public String fromMemberId;
        public String fromMemberName;
        public String toMemberId;
        public String toMemberName;
        public double amount;

        public Settlement(String fromMemberId, String fromMemberName, String toMemberId, String toMemberName,
                double amount) {
            this.fromMemberId = fromMemberId;
            this.fromMemberName = fromMemberName;
            this.toMemberId = toMemberId;
            this.toMemberName = toMemberName;
            this.amount = amount;
        }
    }

    public List<Settlement> simplifyDebts(Map<String, BalanceCalculator.MemberBalance> balances) {
        List<Settlement> settlements = new ArrayList<>();
        List<BalanceCalculator.MemberBalance> creditors = new ArrayList<>();
        List<BalanceCalculator.MemberBalance> debtors = new ArrayList<>();

        // Use a map to track remaining balances for simplification algorithm
        // without mutating the original MemberBalance objects
        Map<String, Double> remainingBalances = new HashMap<>();

        for (BalanceCalculator.MemberBalance b : balances.values()) {
            if (b.netBalance > 0.01) {
                creditors.add(b);
                remainingBalances.put(b.memberId, b.netBalance);
            } else if (b.netBalance < -0.01) {
                debtors.add(b);
                remainingBalances.put(b.memberId, b.netBalance);
            }
        }

        // Sort creditors descending (highest balance first)
        Collections.sort(creditors, (a, b) -> Double.compare(b.netBalance, a.netBalance));
        // Sort debtors ascending (most negative balance first)
        Collections.sort(debtors, (a, b) -> Double.compare(a.netBalance, b.netBalance));

        int i = 0; // creditor index
        int j = 0; // debtor index

        while (i < creditors.size() && j < debtors.size()) {
            BalanceCalculator.MemberBalance creditor = creditors.get(i);
            BalanceCalculator.MemberBalance debtor = debtors.get(j);

            double creditorRemaining = remainingBalances.get(creditor.memberId);
            double debtorRemaining = Math.abs(remainingBalances.get(debtor.memberId));

            double amount = Math.min(debtorRemaining, creditorRemaining);
            amount = Math.round(amount * 100.0) / 100.0;

            if (amount > 0) {
                settlements.add(new Settlement(
                        debtor.memberId,
                        debtor.displayName,
                        creditor.memberId,
                        creditor.displayName,
                        amount));
            }

            // Update local state for algorithm
            remainingBalances.put(creditor.memberId, creditorRemaining - amount);
            remainingBalances.put(debtor.memberId, -(debtorRemaining - amount));

            if (remainingBalances.get(creditor.memberId) < 0.01) {
                i++;
            }
            if (Math.abs(remainingBalances.get(debtor.memberId)) < 0.01) {
                j++;
            }
        }

        return settlements;
    }
}
