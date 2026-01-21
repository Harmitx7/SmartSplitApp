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

import com.example.smartsplitter.MainActivity;
import com.example.smartsplitter.R;

public class AllGroupsFragment extends Fragment {

    private GroupViewModel viewModel;
    private GroupListAdapter adapter;
    private java.util.List<com.example.smartsplitter.data.Group> fullGroupList = new java.util.ArrayList<>();
    private android.widget.EditText etSearch;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_groups, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup Header Back Button
        view.findViewById(R.id.btn_back).setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        etSearch = view.findViewById(R.id.et_search_groups);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_all_groups);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new GroupListAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        // Observe all groups
        viewModel.getAllGroups().observe(getViewLifecycleOwner(), groups -> {
            if (groups != null) {
                fullGroupList = new java.util.ArrayList<>(groups);
                // Apply current search if any
                filterGroups(etSearch.getText().toString());
            }
        });

        adapter.setOnGroupClickListener(group -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToGroupDetail(group.groupId);
            }
        });

        // Search Logic
        etSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterGroups(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
            }
        });
    }

    private void filterGroups(String query) {
        if (fullGroupList == null)
            return;

        java.util.List<com.example.smartsplitter.data.Group> filtered = new java.util.ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            filtered.addAll(fullGroupList);
        } else {
            String q = query.toLowerCase().trim();
            for (com.example.smartsplitter.data.Group g : fullGroupList) {
                if (g.groupName.toLowerCase().contains(q)
                        || (g.description != null && g.description.toLowerCase().contains(q))) {
                    filtered.add(g);
                }
            }

            if (filtered.isEmpty()) {
                android.widget.Toast.makeText(getContext(), "No records found", android.widget.Toast.LENGTH_SHORT)
                        .show();
            }
        }
        adapter.setGroups(filtered);
    }
}
