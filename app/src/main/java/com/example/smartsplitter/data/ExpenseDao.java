package com.example.smartsplitter.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertExpense(Expense expense);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSplits(List<ExpenseSplit> splits);

    @Update
    void updateExpense(Expense expense);

    @Delete
    void deleteExpense(Expense expense);

    // Delete splits for an expense (useful for updates)
    @Query("DELETE FROM expense_splits WHERE expense_id = :expenseId")
    void deleteSplitsForExpense(String expenseId);

    @Transaction
    @Query("SELECT * FROM expenses WHERE group_id = :groupId ORDER BY expense_date DESC")
    LiveData<List<ExpenseWithSplits>> getExpensesForGroup(String groupId);

    @Transaction
    @Query("SELECT * FROM expenses WHERE expense_id = :expenseId")
    ExpenseWithSplits getExpenseWithSplits(String expenseId);

    @Query("SELECT * FROM expenses WHERE group_id = :groupId")
    List<Expense> getExpensesListForGroup(String groupId);

    @Query("SELECT * FROM expense_splits WHERE expense_id = :expenseId")
    List<ExpenseSplit> getSplitsForExpense(String expenseId);

    @Transaction
    @Query("SELECT * FROM expenses ORDER BY expense_date DESC")
    LiveData<List<ExpenseWithSplits>> getAllExpenses();

    @Transaction
    @Query("SELECT * FROM expenses WHERE group_id = :groupId ORDER BY expense_date DESC")
    List<ExpenseWithSplits> getExpensesWithSplitsSync(String groupId);
}
