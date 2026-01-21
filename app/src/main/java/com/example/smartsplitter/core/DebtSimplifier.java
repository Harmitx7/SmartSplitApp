package com.example.smartsplitter.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class DebtSimplifier {

    public static class Settlement {
        public String fromMemberId;
        public String fromMemberName;
        public String toMemberId;
        public String toMemberName;
        public double amount;

        public Settlement(String fromMemberId, String fromMemberName, String toMemberId, String toMemberName, double amount) {
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

        for (BalanceCalculator.MemberBalance b : balances.values()) {
            if (b.netBalance > 0.01) { // Floating point tolerance
                creditors.add(b);
            } else if (b.netBalance < -0.01) {
                debtors.add(b);
            }
        }

        // Sort descending
        Collections.sort(creditors, (a, b) -> Double.compare(b.netBalance, a.netBalance));
        // Sort ascending (most negative first)
        Collections.sort(debtors, (a, b) -> Double.compare(a.netBalance, b.netBalance)); // -100 vs -10, -100 is smaller

        int i = 0; // creditor index
        int j = 0; // debtor index

        while (i < creditors.size() && j < debtors.size()) {
            BalanceCalculator.MemberBalance creditor = creditors.get(i);
            BalanceCalculator.MemberBalance debtor = debtors.get(j);

            // The debtor owes X (negative), creditor is owed Y (positive)
            // Amount to transfer is min(|debtor|, creditor)
            double debtorAmount = Math.abs(debtor.netBalance);
            double creditorAmount = creditor.netBalance;
            
            double amount = Math.min(debtorAmount, creditorAmount);
            amount = Math.round(amount * 100.0) / 100.0; // Round to 2 decimals

            if (amount > 0) {
                settlements.add(new Settlement(
                    debtor.memberId,
                    debtor.displayName,
                    creditor.memberId,
                    creditor.displayName,
                    amount
                ));
            }

            // Update balances locally for the algorithm
            creditor.netBalance -= amount;
            debtor.netBalance += amount; // adding positive amount to negative balance reduces debt

            if (creditor.netBalance < 0.01) {
                i++;
            }
            // Check absolute value because adding a positive amount to negative might make it ~0
            if (Math.abs(debtor.netBalance) < 0.01) {
                j++;
            }
        }

        return settlements;
    }
}
