package com.example.smartsplitter.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartsplitter.R;
import com.example.smartsplitter.data.ExpenseSplit;
import com.example.smartsplitter.data.GroupWithMembers;
import com.example.smartsplitter.data.Member;
import com.example.smartsplitter.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class AddExpenseFragment extends Fragment {

    private static final String ARG_GROUP_ID = "group_id";
    private String groupId;
    private GroupDetailViewModel viewModel;
    private EditText editDesc, editAmount;
    private Spinner spinnerCategory, spinnerPayer;
    private GroupWithMembers currentGroupData;
    private TextView tvEmojiIcon;
    private SessionManager sessionManager;
    private String selectedEmoji = "☺";

    public static AddExpenseFragment newInstance(String groupId) {
        AddExpenseFragment fragment = new AddExpenseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GROUP_ID, groupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupId = getArguments().getString(ARG_GROUP_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_expense, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(GroupDetailViewModel.class);
        if (groupId != null) {
            viewModel.setGroupId(groupId);
        }
        sessionManager = new SessionManager(requireContext());

        editDesc = view.findViewById(R.id.et_description);
        editAmount = view.findViewById(R.id.et_amount);
        spinnerCategory = view.findViewById(R.id.spinner_category);
        spinnerPayer = view.findViewById(R.id.spinner_payer);
        tvEmojiIcon = view.findViewById(R.id.tv_emoji_icon);
        Button btnSave = view.findViewById(R.id.btn_add_expense);

        View btnBack = view.findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        }

        // Setup Category Spinner
        String[] defaultCategories = { "Select Category", "Food", "Transport", "Accommodation", "Entertainment",
                "Shopping", "Other" };
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(getContext(), R.layout.item_dropdown,
                defaultCategories);
        catAdapter.setDropDownViewResource(R.layout.item_dropdown);
        spinnerCategory.setAdapter(catAdapter);

        // Observer for Members to populate Payer spinner
        viewModel.groupWithMembers.observe(getViewLifecycleOwner(), data -> {
            currentGroupData = data;
            if (data != null && data.members != null) {
                List<String> names = new ArrayList<>();
                names.add("Select Payer"); // Default option

                // Get current user info for "(Me)" logic
                String currentName = sessionManager.getUserName();
                String currentUsername = sessionManager.getUsername();

                for (int i = 0; i < data.members.size(); i++) {
                    Member m = data.members.get(i);
                    String displayName = m.displayName;

                    boolean isMe = false;
                    if (currentUsername != null && !currentUsername.isEmpty() && m.username != null
                            && !m.username.isEmpty()) {
                        isMe = currentUsername.equalsIgnoreCase(m.username);
                    } else if (currentName != null) {
                        isMe = currentName.equalsIgnoreCase(m.displayName);
                    }

                    if (isMe) {
                        displayName += " (Me)";
                    }
                    names.add(displayName);
                }

                ArrayAdapter<String> payerAdapter = new ArrayAdapter<>(getContext(),
                        R.layout.item_dropdown, names);
                payerAdapter.setDropDownViewResource(R.layout.item_dropdown);
                spinnerPayer.setAdapter(payerAdapter);
            }
        });

        btnSave.setOnClickListener(v -> saveExpense());

        tvEmojiIcon.setOnClickListener(v -> showEmojiDialog());
    }

    private void showEmojiDialog() {
        final String[] emojis = {
                "🍕", "🍔", "🍻", "☕", "🚕", "✈️", "🏠", "🛒",
                "🎬", "⛽", "💊", "💡", "🎁", "📱", "💇", "🔧",
                "🥗", "🍦", "🏋️", "📚", "🎮", "🎵", "💰", "🏦"
        };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(requireContext());
        builder.setTitle("Pick an Emoji");

        // Use Grid Layout
        android.widget.GridView gridView = new android.widget.GridView(requireContext());
        gridView.setNumColumns(4);
        gridView.setPadding(32, 32, 32, 32);
        gridView.setVerticalSpacing(32);
        gridView.setHorizontalSpacing(32);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_list_item_1, emojis) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                android.widget.TextView tv = (android.widget.TextView) super.getView(position, convertView, parent);
                tv.setTextSize(24);
                tv.setGravity(android.view.Gravity.CENTER);
                return tv;
            }
        };
        gridView.setAdapter(adapter);

        builder.setView(gridView);
        final android.app.Dialog dialog = builder.create();

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            selectedEmoji = emojis[position];
            tvEmojiIcon.setText(selectedEmoji);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void saveExpense() {
        String desc = editDesc.getText().toString();
        String amountStr = editAmount.getText().toString();

        if (desc.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(getContext(), "Please fill details", Toast.LENGTH_SHORT).show();
            return;
        }

        if (spinnerCategory.getSelectedItemPosition() == 0) {
            Toast.makeText(getContext(), "Please select a category", Toast.LENGTH_SHORT).show();
            return;
        }

        if (spinnerPayer.getSelectedItemPosition() == 0) {
            Toast.makeText(getContext(), "Please select who paid", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);
        String category = spinnerCategory.getSelectedItem().toString();

        if (currentGroupData == null || currentGroupData.members == null || currentGroupData.members.isEmpty()) {
            Toast.makeText(getContext(), "No members in group", Toast.LENGTH_SHORT).show();
            return;
        }

        // Adjust index for "Select Payer" offset
        int payerIndex = spinnerPayer.getSelectedItemPosition() - 1;
        Member payer = currentGroupData.members.get(payerIndex);

        List<ExpenseSplit> splits = new ArrayList<>();
        double splitAmount = amount / currentGroupData.members.size();

        for (Member m : currentGroupData.members) {
            splits.add(new ExpenseSplit("temp", m.memberId, splitAmount, "equal"));
        }

        viewModel.addExpense(selectedEmoji + " " + desc, amount, currentGroupData.group.currency,
                payer.memberId, category, splits);

        android.widget.Toast.makeText(getContext(), "Expense added", android.widget.Toast.LENGTH_SHORT).show();
        getParentFragmentManager().popBackStack();
    }
}
