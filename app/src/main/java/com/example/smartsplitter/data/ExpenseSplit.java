package com.example.smartsplitter.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(
    tableName = "expense_splits",
    foreignKeys = {
        @ForeignKey(
            entity = Expense.class,
            parentColumns = "expense_id",
            childColumns = "expense_id",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Member.class,
            parentColumns = "member_id",
            childColumns = "member_id",
            onDelete = ForeignKey.CASCADE
        )
    },
    indices = {@Index(value = "expense_id"), @Index(value = "member_id")}
)
public class ExpenseSplit {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "split_id")
    public String splitId;

    @NonNull
    @ColumnInfo(name = "expense_id")
    public String expenseId;

    @NonNull
    @ColumnInfo(name = "member_id")
    public String memberId;

    @ColumnInfo(name = "amount")
    public double amount;

    @NonNull
    @ColumnInfo(name = "split_type")
    public String splitType; // 'equal', 'unequal', 'percentage', 'shares'

    @ColumnInfo(name = "created_at")
    public long createdAt;

    @Ignore
    public ExpenseSplit(@NonNull String expenseId, @NonNull String memberId, double amount, @NonNull String splitType) {
        this.splitId = UUID.randomUUID().toString();
        this.expenseId = expenseId;
        this.memberId = memberId;
        this.amount = amount;
        this.splitType = splitType;
        this.createdAt = System.currentTimeMillis();
    }

    public ExpenseSplit() {}
}
