package com.example.smartsplitter.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartsplitter.core.DebtSimplifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SettlementAdapter extends RecyclerView.Adapter<SettlementAdapter.SettlementViewHolder> {

    private List<DebtSimplifier.Settlement> settlements = new ArrayList<>();
    private OnSettlementClickListener listener;

    public interface OnSettlementClickListener {
        void onSettlementClick(DebtSimplifier.Settlement settlement);
    }

    public void setOnSettlementClickListener(OnSettlementClickListener listener) {
        this.listener = listener;
    }

    public void setSettlements(List<DebtSimplifier.Settlement> settlements) {
        this.settlements = settlements;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SettlementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(com.example.smartsplitter.R.layout.item_settlement, parent, false);
        return new SettlementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettlementViewHolder holder, int position) {
        DebtSimplifier.Settlement item = settlements.get(position);
        holder.textDetail.setText(String.format("%s pays %s", item.fromMemberName, item.toMemberName));
        holder.textAmount.setText(String.format(Locale.getDefault(), "₹%.2f", item.amount));

        holder.btnPay.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSettlementClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return settlements.size();
    }

    static class SettlementViewHolder extends RecyclerView.ViewHolder {
        TextView textDetail;
        TextView textAmount;
        android.widget.Button btnPay;

        public SettlementViewHolder(@NonNull View itemView) {
            super(itemView);
            textDetail = itemView.findViewById(com.example.smartsplitter.R.id.tv_settlement_detail);
            textAmount = itemView.findViewById(com.example.smartsplitter.R.id.tv_settlement_amount);
            btnPay = itemView.findViewById(com.example.smartsplitter.R.id.btn_settle_now);
        }
    }
}
