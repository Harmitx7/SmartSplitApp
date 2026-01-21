package com.example.smartsplitter.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartsplitter.R;
import com.example.smartsplitter.data.ExpenseWithSplits;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder> {

    private List<ExpenseWithSplits> expenses = new ArrayList<>();

    public void setExpenses(List<ExpenseWithSplits> expenses) {
        this.expenses = expenses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense_card, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        ExpenseWithSplits item = expenses.get(position);
        
        holder.textDesc.setText(item.expense.description);
        holder.textAmount.setText(String.format(Locale.getDefault(), "-%.2f %s", item.expense.totalAmount, item.expense.currency));
        holder.textPayer.setText("Paid by Member");
        holder.textDate.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(new Date(item.expense.expenseDate)));
        
        // Category Icon Mapping - using drawable resources instead of emojis
        int iconResId = R.drawable.ic_other;
        if (item.expense.category != null) {
            switch (item.expense.category.toLowerCase()) {
                case "food": iconResId = R.drawable.ic_food; break;
                case "transport": iconResId = R.drawable.ic_transport; break;
                case "accommodation": iconResId = R.drawable.ic_hotel; break;
                case "entertainment": iconResId = R.drawable.ic_entertainment; break;
            }
        }
        holder.imageCategory.setImageResource(iconResId);
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView textDesc, textAmount, textPayer, textDate;
        ImageView imageCategory;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            textDesc = itemView.findViewById(R.id.tv_expense_description);
            textAmount = itemView.findViewById(R.id.tv_expense_amount);
            textPayer = itemView.findViewById(R.id.tv_expense_payer);
            textDate = itemView.findViewById(R.id.tv_expense_date);
            imageCategory = itemView.findViewById(R.id.iv_category_icon);
        }
    }
}
