package com.example.smartsplitter.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartsplitter.R;
import com.example.smartsplitter.MainActivity;

public class ExpenseListFragment extends Fragment {

    private static final String ARG_GROUP_ID = "group_id";
    private String groupId;
    private GroupDetailViewModel viewModel;
    private ExpenseListAdapter adapter;

    public static ExpenseListFragment newInstance(String groupId) {
        ExpenseListFragment fragment = new ExpenseListFragment();
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
        return inflater.inflate(R.layout.fragment_expense_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Scope to Parent Fragment! Basic rule for shared VMs in ViewPager
        // Actually, GroupDetailFragment hosts the VP. So we want that VM.
        // If we use requireParentFragment(), we can get its VM.
        if (getParentFragment() != null) {
            viewModel = new ViewModelProvider(getParentFragment()).get(GroupDetailViewModel.class);
        } else {
             // Fallback
            viewModel = new ViewModelProvider(requireActivity()).get(GroupDetailViewModel.class);
        }

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_expenses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ExpenseListAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.expenses.observe(getViewLifecycleOwner(), list -> {
            adapter.setExpenses(list);
        });

        view.findViewById(R.id.fab_add_expense).setOnClickListener(v -> {
            ((MainActivity) getActivity()).showAddExpenseFragment();
        });
    }
}
