package com.example.smartsplitter.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartsplitter.R;
import com.example.smartsplitter.data.AppDatabase;
import com.example.smartsplitter.data.Expense;
import com.example.smartsplitter.data.Group;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;

public class StatsFragment extends Fragment {

    private TextView tvTotalSpending;
    private TextView tvTotalGroups;
    private TextView tvTotalExpenses;
    private LinearLayout layoutCategories;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTotalSpending = view.findViewById(R.id.tv_total_spending);
        tvTotalGroups = view.findViewById(R.id.tv_total_groups);
        tvTotalExpenses = view.findViewById(R.id.tv_total_expenses);
        layoutCategories = view.findViewById(R.id.layout_categories);

        view.findViewById(R.id.btn_back).setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        // Groups block click
        view.findViewById(R.id.btn_stats_groups).setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AllGroupsFragment())
                    .addToBackStack(null)
                    .commit();
        });

        loadStats();
    }

    private void loadStats() {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(requireContext());

            String targetGroupId = getArguments() != null ? getArguments().getString("GROUP_ID") : null;
            List<Group> groups;

            if (targetGroupId != null) {
                // Filter for single group
                groups = new java.util.ArrayList<>();
                // Fetch all and filter (safest without DAO change)
                List<Group> all = db.groupDao().getAllActiveGroupsSync();
                if (all != null) {
                    for (Group g : all) {
                        if (g.groupId.equals(targetGroupId)) {
                            groups.add(g);
                            break;
                        }
                    }
                }
            } else {
                groups = db.groupDao().getAllActiveGroupsSync();
            }

            int groupCount = groups != null ? groups.size() : 0;

            // Get all expenses and calculate totals
            double totalSpending = 0;
            int expenseCount = 0;
            Map<String, Double> categoryTotals = new HashMap<>();

            if (groups != null) {
                for (Group group : groups) {
                    List<Expense> expenses = db.expenseDao().getExpensesListForGroup(group.groupId);
                    if (expenses != null) {
                        expenseCount += expenses.size();
                        for (Expense expense : expenses) {
                            totalSpending += expense.totalAmount;

                            // Track by category
                            String cat = expense.category != null ? expense.category : "Other";
                            categoryTotals.put(cat, categoryTotals.getOrDefault(cat, 0.0) + expense.totalAmount);
                        }
                    }
                }
            }

            final Map<String, Double> finalCategories = categoryTotals;
            final double finalTotal = totalSpending;
            final int finalGroupCount = groupCount;
            final int finalExpenseCount = expenseCount;

            // Calculate Graph Data (Sorted by date)
            // Simplified: Gather all expenses, sort by date, bin by day/order?
            // Or just last N expenses for trend line.
            // Let's take last 10 dates with activity.

            // Use TreeMap for sorted keys
            java.util.TreeMap<Long, Double> dailyTotals = new java.util.TreeMap<>();
            if (groups != null) {
                for (Group group : groups) {
                    List<Expense> groupExpenses = db.expenseDao().getExpensesListForGroup(group.groupId);
                    if (groupExpenses != null) {
                        for (Expense e : groupExpenses) {
                            // Normalize date to day if needed, or just use raw timestamp approximation
                            dailyTotals.put(e.expenseDate,
                                    dailyTotals.getOrDefault(e.expenseDate, 0.0) + e.totalAmount);
                        }
                    }
                }
            }

            final java.util.List<Float> graphPoints = new java.util.ArrayList<>();
            for (Double d : dailyTotals.values()) {
                graphPoints.add(d.floatValue());
            }

            requireActivity().runOnUiThread(() -> {
                tvTotalSpending.setText(String.format(Locale.getDefault(), "₹%.2f", finalTotal));
                tvTotalGroups.setText(String.valueOf(finalGroupCount));
                tvTotalExpenses.setText(String.valueOf(finalExpenseCount));

                if (getView() != null) {
                    SimpleTrendGraph graph = getView().findViewById(R.id.graph_trend);
                    if (graph != null) {
                        graph.setData(graphPoints);
                    }
                }

                // Build category cards
                layoutCategories.removeAllViews();
                for (Map.Entry<String, Double> entry : finalCategories.entrySet()) {
                    View categoryCard = createCategoryCard(entry.getKey(), entry.getValue(), finalTotal);
                    layoutCategories.addView(categoryCard);
                }

                if (finalCategories.isEmpty()) {
                    TextView empty = new TextView(getContext());
                    empty.setText("No expenses recorded yet");
                    empty.setTextColor(getResources().getColor(R.color.text_muted));
                    layoutCategories.addView(empty);
                }
            });
        });
    }

    private View createCategoryCard(String category, double amount, double total) {
        LinearLayout card = new LinearLayout(getContext());
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setBackgroundResource(R.drawable.card_dark_background);
        card.setPadding(32, 24, 32, 24);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 16;
        card.setLayoutParams(params);
        card.setGravity(android.view.Gravity.CENTER_VERTICAL);

        // Icon
        android.widget.ImageView icon = new android.widget.ImageView(getContext());
        int iconResId = getCategoryIconRes(category);
        icon.setImageResource(iconResId);
        icon.setBackgroundResource(R.drawable.icon_background);
        int size = (int) (40 * getResources().getDisplayMetrics().density);
        int padding = (int) (8 * getResources().getDisplayMetrics().density);
        icon.setPadding(padding, padding, padding, padding);
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(size, size);
        icon.setLayoutParams(iconParams);
        // icon.setColorFilter(getResources().getColor(R.color.accent_lime)); //
        // Optional tint
        card.addView(icon);

        // Category name
        TextView name = new TextView(getContext());
        name.setText(category);
        name.setTextColor(getResources().getColor(R.color.text_on_dark));
        name.setTextSize(16);
        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,
                1);
        nameParams.leftMargin = 24;
        name.setLayoutParams(nameParams);
        card.addView(name);

        // Amount
        TextView amountView = new TextView(getContext());
        amountView.setText(String.format(Locale.getDefault(), "₹%.2f", amount));
        amountView.setTextColor(getResources().getColor(R.color.accent_lime));
        amountView.setTextSize(16);
        card.addView(amountView);

        return card;
    }

    private int getCategoryIconRes(String category) {
        if (category == null)
            return R.drawable.ic_document;
        switch (category.toLowerCase()) {
            case "food":
                return R.drawable.ic_food;
            case "transport":
                return R.drawable.ic_transport;
            case "accommodation":
                return R.drawable.ic_hotel;
            case "entertainment":
                return R.drawable.ic_entertainment;
            case "shopping":
                return R.drawable.ic_category;
            default:
                return R.drawable.ic_other;
        }
    }
}
