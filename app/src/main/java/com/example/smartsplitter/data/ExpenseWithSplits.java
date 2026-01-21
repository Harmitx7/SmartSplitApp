package com.example.smartsplitter.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ExpenseWithSplits {
    @Embedded
    public Expense expense;

    @Relation(
        parentColumn = "expense_id",
        entityColumn = "expense_id"
    )
    public List<ExpenseSplit> splits;
}
