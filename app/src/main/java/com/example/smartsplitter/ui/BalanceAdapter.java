package com.example.smartsplitter.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartsplitter.R;
import com.example.smartsplitter.core.BalanceCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BalanceAdapter extends RecyclerView.Adapter<BalanceAdapter.BalanceViewHolder> {

    private List<BalanceCalculator.MemberBalance> balances = new ArrayList<>();

    public void setBalances(List<BalanceCalculator.MemberBalance> balances) {
        this.balances = balances;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BalanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new BalanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BalanceViewHolder holder, int position) {
        BalanceCalculator.MemberBalance item = balances.get(position);
        holder.textName.setText(item.displayName != null ? item.displayName : "Member");

        if (item.netBalance > 0) {
            holder.textBalance.setText(String.format(Locale.getDefault(), "Gets back ₹%.2f", item.netBalance));
            holder.textBalance.setTextColor(Color.parseColor("#34C759")); // Green
        } else if (item.netBalance < 0) {
            holder.textBalance.setText(String.format(Locale.getDefault(), "Owes ₹%.2f", Math.abs(item.netBalance)));
            holder.textBalance.setTextColor(Color.parseColor("#FF3B30")); // Red
        } else {
            holder.textBalance.setText("Settled up");
            holder.textBalance.setTextColor(Color.GRAY);
        }
    }

    @Override
    public int getItemCount() {
        return balances.size();
    }

    static class BalanceViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textBalance;

        public BalanceViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(android.R.id.text1);
            textBalance = itemView.findViewById(android.R.id.text2);
        }
    }
}
