package com.example.smartsplitter.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartsplitter.R;
import com.example.smartsplitter.data.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupViewHolder> {

    private List<com.example.smartsplitter.data.GroupWithMembers> groups = new ArrayList<>();
    private OnGroupClickListener listener;

    public interface OnGroupClickListener {
        void onGroupClick(com.example.smartsplitter.data.Group group);
    }

    public void setOnGroupClickListener(OnGroupClickListener listener) {
        this.listener = listener;
    }

    public void setGroups(List<com.example.smartsplitter.data.GroupWithMembers> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group_card, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        com.example.smartsplitter.data.GroupWithMembers data = groups.get(position);
        com.example.smartsplitter.data.Group group = data.group;
        holder.textName.setText(group.groupName);
        holder.textCurrency.setText(group.currency);

        int memberCount = data.members != null ? data.members.size() : 0;
        holder.textMembers.setText(memberCount + (memberCount == 1 ? " member" : " members"));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onGroupClick(group);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textCurrency;
        TextView textMembers;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.tv_group_name);
            textCurrency = itemView.findViewById(R.id.tv_group_currency);
            textMembers = itemView.findViewById(R.id.tv_group_members);
        }
    }
}
