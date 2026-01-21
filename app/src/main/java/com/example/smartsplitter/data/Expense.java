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
    tableName = "expenses",
    foreignKeys = {
        @ForeignKey(
            entity = Group.class,
            parentColumns = "group_id",
            childColumns = "group_id",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Member.class,
            parentColumns = "member_id",
            childColumns = "payer_member_id",
            onDelete = ForeignKey.RESTRICT // Prevent deleting a member if they have paid for expenses still in history? Or CASCADE? PRD says "Members are 'discovered'". But if manually added, we shouldn't delete if used.
        )
    },
    indices = {
        @Index(value = "group_id"),
        @Index(value = "payer_member_id")
    }
)
public class Expense {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "expense_id")
    public String expenseId;

    @NonNull
    @ColumnInfo(name = "group_id")
    public String groupId;

    @NonNull
    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "total_amount")
    public double totalAmount;

    @NonNull
    @ColumnInfo(name = "currency")
    public String currency;

    @NonNull
    @ColumnInfo(name = "payer_member_id")
    public String payerMemberId;

    @NonNull
    @ColumnInfo(name = "category")
    public String category; // e.g., "Food", "Transport"

    @ColumnInfo(name = "expense_date")
    public long expenseDate;

    @ColumnInfo(name = "created_at")
    public long createdAt;

    @ColumnInfo(name = "updated_at")
    public long updatedAt;

    @ColumnInfo(name = "is_settlement")
    public boolean isSettlement;

    @Ignore
    public Expense(@NonNull String groupId, @NonNull String description, double totalAmount, 
                   @NonNull String currency, @NonNull String payerMemberId, @NonNull String category, 
                   long expenseDate, boolean isSettlement) {
        this.expenseId = UUID.randomUUID().toString();
        this.groupId = groupId;
        this.description = description;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.payerMemberId = payerMemberId;
        this.category = category;
        this.expenseDate = expenseDate;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.isSettlement = isSettlement;
    }

    public Expense() {}
}
