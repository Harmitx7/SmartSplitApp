package com.example.smartsplitter.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.smartsplitter.core.BalanceCalculator;
import com.example.smartsplitter.core.DebtSimplifier;
import com.example.smartsplitter.data.AppRepository;
import com.example.smartsplitter.data.Expense;
import com.example.smartsplitter.data.ExpenseSplit;
import com.example.smartsplitter.data.ExpenseWithSplits;
import com.example.smartsplitter.data.GroupWithMembers;

import java.util.List;
import java.util.Map;

public class GroupDetailViewModel extends AndroidViewModel {

    private AppRepository repository;
    private final MutableLiveData<String> groupId = new MutableLiveData<>();

    // LiveData transformations
    public LiveData<GroupWithMembers> groupWithMembers;
    public LiveData<List<ExpenseWithSplits>> expenses;

    public GroupDetailViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);

        groupWithMembers = Transformations.switchMap(groupId, id -> repository.getGroupWithMembers(id));

        expenses = Transformations.switchMap(groupId, id -> repository.getExpensesForGroup(id));
    }

    public void setGroupId(String id) {
        groupId.setValue(id);
    }

    public void addMemberByUsername(String username, AppRepository.OnMemberAddResult callback) {
        String currentGroupId = groupId.getValue();
        if (currentGroupId == null) {
            callback.onResult(false, "No active group");
            return;
        }
        repository.addMemberByUsername(currentGroupId, username, callback);
    }

    public void searchUsers(String query,
            androidx.core.util.Consumer<java.util.List<com.example.smartsplitter.data.User>> callback) {
        java.util.concurrent.Executors.newSingleThreadExecutor().execute(() -> {
            java.util.List<com.example.smartsplitter.data.User> results = repository.searchUsers(query);
            callback.accept(results);
        });
    }

    public void addExpense(String description, double amount, String currency,
            String payerId, String category, List<ExpenseSplit> splits) {
        String currentGroupId = groupId.getValue();
        if (currentGroupId == null)
            return;

        Expense expense = new Expense(currentGroupId, description, amount, currency, payerId, category,
                System.currentTimeMillis(), false);

        // Fix up splits to reference the new expense ID
        for (ExpenseSplit split : splits) {
            split.expenseId = expense.expenseId;
        }

        repository.insertExpense(expense, splits);
    }

    public void recordSettlement(DebtSimplifier.Settlement settlement) {
        String currentGroupId = groupId.getValue();
        if (currentGroupId == null)
            return;

        // Create a settlement expense
        // Description: "Settlement from A to B"
        String desc = "Settlement: " + settlement.fromMemberName + " -> " + settlement.toMemberName;

        android.util.Log.d("ViewModel", "Recording settlement: " + desc + " Amount: " + settlement.amount);

        Expense expense = new Expense(
                currentGroupId,
                desc,
                settlement.amount,
                "INR", // Default or fetch from settings
                settlement.fromMemberId, // Payer is the one who owes
                "Settlement",
                System.currentTimeMillis(),
                true // isSettlement flag (if supported, else just category)
        );

        // One split: The recipient gets 100% of the benefit (paid amount)
        java.util.List<ExpenseSplit> splits = new java.util.ArrayList<>();
        splits.add(new ExpenseSplit("", settlement.toMemberId, settlement.amount, "settlement")); // Expense ID set in
                                                                                                  // repository

        repository.insertExpense(expense, splits);
    }

    // Simplistic approach for balances: We might need to run this on background and
    // post to LiveData
    // For now, we'll let the Fragment manage calling the calculator with the
    // List<ExpenseWithSplits> it observes.
}
