package com.example.smartsplitter.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartsplitter.R;
import com.example.smartsplitter.core.BalanceCalculator;
import com.example.smartsplitter.core.DebtSimplifier;
import com.example.smartsplitter.data.ExpenseWithSplits;
import com.example.smartsplitter.data.GroupWithMembers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BalancesFragment extends Fragment {

    private GroupDetailViewModel viewModel;
    private BalanceAdapter balanceAdapter;
    private SettlementAdapter settlementAdapter;
    private BalanceCalculator calculator = new BalanceCalculator();
    private DebtSimplifier simplifier = new DebtSimplifier();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balances, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(GroupDetailViewModel.class);

        RecyclerView recyclerBalances = view.findViewById(R.id.recycler_balances);
        recyclerBalances.setLayoutManager(new LinearLayoutManager(getContext()));
        balanceAdapter = new BalanceAdapter();
        recyclerBalances.setAdapter(balanceAdapter);

        RecyclerView recyclerSettlements = view.findViewById(R.id.recycler_settlements);
        recyclerSettlements.setLayoutManager(new LinearLayoutManager(getContext()));
        settlementAdapter = new SettlementAdapter();

        // Connect the click listener to the dialog
        settlementAdapter.setOnSettlementClickListener(this::showSettleDialog);

        recyclerSettlements.setAdapter(settlementAdapter);

        // Observer pattern: When expenses OR members change, re-calculate
        viewModel.expenses.observe(getViewLifecycleOwner(), expenses -> {
            GroupWithMembers groupData = viewModel.groupWithMembers.getValue();
            if (groupData != null && groupData.members != null) {
                calculateAndDisplay(expenses, groupData);
            }
        });

        // Also observe group details
        viewModel.groupWithMembers.observe(getViewLifecycleOwner(), groupData -> {
            List<ExpenseWithSplits> expenseList = viewModel.expenses.getValue();
            if (expenseList != null && groupData != null) {
                calculateAndDisplay(expenseList, groupData);
            }
        });

        view.findViewById(R.id.btn_export_pdf).setOnClickListener(v -> {
            GroupWithMembers groupData = viewModel.groupWithMembers.getValue();
            List<ExpenseWithSplits> expenses = viewModel.expenses.getValue();

            if (groupData != null && expenses != null) {
                Map<String, BalanceCalculator.MemberBalance> balances = calculator.calculateBalances(expenses,
                        groupData.members);
                // Run on background thread ideally, or simple thread for now
                new Thread(() -> {
                    com.example.smartsplitter.core.PdfGenerator.generateGroupReport(getContext(), groupData, expenses,
                            balances);
                    getActivity().runOnUiThread(() -> {
                        android.widget.Toast
                                .makeText(getContext(), "Report saved to Downloads", android.widget.Toast.LENGTH_LONG)
                                .show();
                    });
                }).start();
            }
        });
    }

    private void showSettleDialog(DebtSimplifier.Settlement settlement) {
        if (settlement.amount <= 0)
            return;

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(
                requireContext());
        builder.setTitle("Confirm Payment");
        builder.setMessage(String.format("Record payment of ₹%.2f from %s to %s?",
                settlement.amount, settlement.fromMemberName, settlement.toMemberName));

        builder.setPositiveButton("Confirm", (dialog, which) -> {
            DebtSimplifier.Settlement finalSettlement = new DebtSimplifier.Settlement(
                    settlement.fromMemberId, settlement.fromMemberName,
                    settlement.toMemberId, settlement.toMemberName,
                    settlement.amount);
            viewModel.recordSettlement(finalSettlement);
            android.widget.Toast.makeText(getContext(),
                    "Payment Recorded: ₹" + String.format("%.2f", settlement.amount), android.widget.Toast.LENGTH_SHORT)
                    .show();
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void calculateAndDisplay(List<ExpenseWithSplits> expenses, GroupWithMembers groupData) {
        Map<String, BalanceCalculator.MemberBalance> balances = calculator.calculateBalances(expenses,
                groupData.members);
        balanceAdapter.setBalances(new ArrayList<>(balances.values()));

        List<DebtSimplifier.Settlement> settlements = simplifier.simplifyDebts(balances);
        settlementAdapter.setSettlements(settlements);
    }
}
