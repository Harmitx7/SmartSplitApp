package com.example.smartsplitter.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartsplitter.R;
import com.example.smartsplitter.MainActivity;

public class GroupListFragment extends Fragment {

    private GroupViewModel viewModel;
    private GroupListAdapter adapter;
    private TextView tvGroupCount;
    private View emptyState;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvGroupCount = view.findViewById(R.id.tv_group_count);
        TextView tvTotalBalance = view.findViewById(R.id.tv_total_balance);
        emptyState = view.findViewById(R.id.empty_state);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_groups);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);

        adapter = new GroupListAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        viewModel.getAllGroupsWithMembers().observe(getViewLifecycleOwner(), groups -> {
            adapter.setGroups(groups);

            // Update count
            int count = groups != null ? groups.size() : 0;
            tvGroupCount.setText(String.valueOf(count));

            // Show/hide empty state
            if (count == 0) {
                emptyState.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                tvTotalBalance.setText("₹ 0.00");
            } else {
                emptyState.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                // Calculate Total Balance logic
                // For balance calc, we need to extract Groups
                java.util.List<com.example.smartsplitter.data.Group> justGroups = new java.util.ArrayList<>();
                for (com.example.smartsplitter.data.GroupWithMembers gwm : groups) {
                    justGroups.add(gwm.group);
                }
                calculateTotalBalance(justGroups, tvTotalBalance);
            }
        });

        adapter.setOnGroupClickListener(group -> {
            ((MainActivity) getActivity()).navigateToGroupDetail(group.groupId);
        });

        // Profile Avatar Logic
        setupProfileHeader(view);

        // New button bindings for redesigned layout
        view.findViewById(R.id.btn_create_group).setOnClickListener(v -> {
            ((MainActivity) getActivity()).showCreateGroupFragment();
        });

        view.findViewById(R.id.btn_join_group).setOnClickListener(v -> {
            ((MainActivity) getActivity()).showJoinGroupFragment();
        });

        view.findViewById(R.id.btn_stats).setOnClickListener(v -> {
            ((MainActivity) getActivity()).showStatsFragment();
        });

        view.findViewById(R.id.btn_settings).setOnClickListener(v -> {
            ((MainActivity) getActivity()).showSettingsFragment();
        });

        // View All Groups
        view.findViewById(R.id.tv_view_all).setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AllGroupsFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void setupProfileHeader(View view) {
        androidx.cardview.widget.CardView cardAvatar = view.findViewById(R.id.card_profile_avatar);
        TextView tvInitials = view.findViewById(R.id.tv_home_profile_initials);
        android.widget.ImageView ivImage = view.findViewById(R.id.iv_home_profile_image);

        com.example.smartsplitter.utils.SessionManager session = new com.example.smartsplitter.utils.SessionManager(
                requireContext());
        String name = session.getUserName();
        String email = session.getUserEmail();

        // 1. Set Initials
        if (name != null && !name.isEmpty()) {
            String[] parts = name.trim().split("\\s+");
            String initials = "";
            if (parts.length > 0 && !parts[0].isEmpty()) {
                initials += String.valueOf(parts[0].charAt(0)).toUpperCase();
                if (parts.length > 1 && !parts[parts.length - 1].isEmpty()) {
                    initials += String.valueOf(parts[parts.length - 1].charAt(0)).toUpperCase();
                }
            }
            tvInitials.setText(initials);
        } else {
            tvInitials.setText("U");
        }

        // 2. Load Image from DB
        com.example.smartsplitter.data.AppDatabase db = com.example.smartsplitter.data.AppDatabase
                .getDatabase(requireContext());
        java.util.concurrent.Executors.newSingleThreadExecutor().execute(() -> {
            try {
                com.example.smartsplitter.data.User user = db.userDao().getUserByEmail(email);
                if (user != null && user.profileImageUri != null && !user.profileImageUri.isEmpty()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            android.net.Uri uri = android.net.Uri.parse(user.profileImageUri);
                            ivImage.setImageURI(uri);
                            ivImage.setVisibility(View.VISIBLE);
                            tvInitials.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 3. Click Listener
        cardAvatar.setOnClickListener(v -> {
            showProfileEditDialog();
        });
    }

    private void showProfileEditDialog() {
        new com.example.smartsplitter.ui.ProfileEditBottomSheet().show(getParentFragmentManager(), "profile_edit");
    }

    private void calculateTotalBalance(java.util.List<com.example.smartsplitter.data.Group> groups,
            TextView tvBalance) {
        if (groups == null || groups.isEmpty())
            return;

        com.example.smartsplitter.utils.SessionManager session = new com.example.smartsplitter.utils.SessionManager(
                requireContext());
        String currentEmail = session.getUserEmail();

        com.example.smartsplitter.data.AppDatabase db = com.example.smartsplitter.data.AppDatabase
                .getDatabase(requireContext());

        java.util.concurrent.Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // Get current username
                com.example.smartsplitter.data.User currentUser = db.userDao().getUserByEmail(currentEmail);
                if (currentUser == null)
                    return;
                String myUsername = currentUser.username;

                double totalBalance = 0.0;
                com.example.smartsplitter.core.BalanceCalculator calculator = new com.example.smartsplitter.core.BalanceCalculator();

                for (com.example.smartsplitter.data.Group group : groups) {
                    // Fetch expenses
                    java.util.List<com.example.smartsplitter.data.ExpenseWithSplits> expenses = db.expenseDao()
                            .getExpensesWithSplitsSync(group.groupId);

                    if (expenses == null)
                        continue;

                    java.util.List<com.example.smartsplitter.data.Member> members = db.groupDao()
                            .getGroupMembersSync(group.groupId);

                    // Find my memberId in this group
                    String myMemberId = null;
                    if (members != null) {
                        for (com.example.smartsplitter.data.Member m : members) {
                            if (m.username != null && m.username.equals(myUsername)) {
                                myMemberId = m.memberId;
                                break;
                            }
                            if (m.displayName.equals(currentUser.name)) {
                                myMemberId = m.memberId;
                                break;
                            }
                        }
                    }

                    if (myMemberId != null) {
                        java.util.Map<String, com.example.smartsplitter.core.BalanceCalculator.MemberBalance> balances = calculator
                                .calculateBalances(expenses, members);
                        if (balances.containsKey(myMemberId)) {
                            totalBalance += balances.get(myMemberId).netBalance;
                        }
                    }
                }

                double finalTotal = totalBalance;
                requireActivity().runOnUiThread(() -> {
                    if (tvBalance != null) {
                        String symbol = "₹";
                        if (finalTotal >= 0) {
                            tvBalance.setText(
                                    "+" + symbol + String.format(java.util.Locale.getDefault(), "%.2f", finalTotal));
                            tvBalance.setTextColor(getResources().getColor(R.color.accent_lime));
                        } else {
                            tvBalance
                                    .setText(symbol + String.format(java.util.Locale.getDefault(), "%.2f", finalTotal));
                            tvBalance.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
