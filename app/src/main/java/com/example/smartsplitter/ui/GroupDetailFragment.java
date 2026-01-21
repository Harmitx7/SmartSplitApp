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
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.smartsplitter.R;
import com.example.smartsplitter.data.ExpenseWithSplits;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class GroupDetailFragment extends Fragment {

    private static final String ARG_GROUP_ID = "group_id";
    private String groupId;
    private GroupDetailViewModel viewModel;
    private TextView tvGroupName;
    private TextView tvTotalBalance;

    public static GroupDetailFragment newInstance(String groupId) {
        GroupDetailFragment fragment = new GroupDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GROUP_ID, groupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupId = getArguments().getString(ARG_GROUP_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvGroupName = view.findViewById(R.id.tv_group_name);
        tvTotalBalance = view.findViewById(R.id.tv_total_balance);

        // Back navigation on group name click (optional)
        tvGroupName.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        viewModel = new ViewModelProvider(requireActivity()).get(GroupDetailViewModel.class);
        viewModel.setGroupId(groupId);

        viewModel.groupWithMembers.observe(getViewLifecycleOwner(), groupWithMembers -> {
            if (groupWithMembers != null && groupWithMembers.group != null) {
                tvGroupName.setText(groupWithMembers.group.groupName);
            }
        });

        // Calculate total balance from expenses
        viewModel.expenses.observe(getViewLifecycleOwner(), expenses -> {
            double total = 0;
            if (expenses != null) {
                for (ExpenseWithSplits e : expenses) {
                    total += e.expense.totalAmount;
                }
            }
            tvTotalBalance.setText(String.format("₹%.2f", total));
        });

        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

        viewPager.setAdapter(new GroupPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Expenses");
                    break;
                case 1:
                    tab.setText("Balances");
                    break;
                case 2:
                    tab.setText("Members");
                    break;
            }
        }).attach();

        // Export PDF button
        view.findViewById(R.id.btn_export_pdf).setOnClickListener(v -> {
            if (viewModel.groupWithMembers.getValue() != null && viewModel.expenses.getValue() != null) {
                com.example.smartsplitter.utils.PdfUtil.exportGroupExpenses(requireContext(),
                        viewModel.groupWithMembers.getValue().group,
                        viewModel.expenses.getValue());
            } else {
                android.widget.Toast.makeText(getContext(), "Loading data...", android.widget.Toast.LENGTH_SHORT)
                        .show();
            }
        });

        // Share button
        view.findViewById(R.id.btn_share).setOnClickListener(v -> {
            if (viewModel.groupWithMembers.getValue() != null) {
                ShareGroupDialogFragment dialog = ShareGroupDialogFragment
                        .newInstance(viewModel.groupWithMembers.getValue().group);
                dialog.show(getParentFragmentManager(), "ShareGroupDialog");
            }
        });

        // Stats button
        view.findViewById(R.id.btn_stats).setOnClickListener(v -> {
            StatsFragment statsFragment = new StatsFragment();
            Bundle args = new Bundle();
            args.putString("GROUP_ID", groupId);
            statsFragment.setArguments(args);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, statsFragment) // MainActivity container
                    .addToBackStack(null)
                    .commit();
        });
    }

    private class GroupPagerAdapter extends FragmentStateAdapter {
        public GroupPagerAdapter(Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return ExpenseListFragment.newInstance(groupId);
                case 1:
                    return new BalancesFragment();
                case 2:
                    return new MembersFragment();
                default:
                    return new Fragment();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}
