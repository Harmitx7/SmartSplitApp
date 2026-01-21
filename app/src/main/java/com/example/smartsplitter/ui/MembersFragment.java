package com.example.smartsplitter.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartsplitter.R;
import com.example.smartsplitter.data.GroupWithMembers;
import com.example.smartsplitter.data.Member;

import java.util.ArrayList;
import java.util.List;

public class MembersFragment extends Fragment {

    private GroupDetailViewModel viewModel;
    private ListView listView;
    private MemberAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        // Using simple list view for variation/simplicity for member list
        return inflater.inflate(R.layout.fragment_members, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(GroupDetailViewModel.class);

        listView = view.findViewById(R.id.list_members);
        adapter = new MemberAdapter(getContext(), new ArrayList<>());
        listView.setAdapter(adapter);

        viewModel.groupWithMembers.observe(getViewLifecycleOwner(), data -> {
            if (data != null && data.members != null) {
                adapter.clear();
                adapter.addAll(data.members);
                adapter.notifyDataSetChanged();
            }
        });

        view.findViewById(R.id.btn_share_group).setOnClickListener(v -> {
            GroupWithMembers data = viewModel.groupWithMembers.getValue();
            if (data != null && data.group != null) {
                ShareGroupDialogFragment dialog = ShareGroupDialogFragment.newInstance(data.group);
                dialog.show(getParentFragmentManager(), "ShareGroupDialog");
            }
        });

        view.findViewById(R.id.btn_add_member).setOnClickListener(v -> showAddMemberDialog());
    }

    private void showAddMemberDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(requireContext());
        // Custom View
        android.widget.LinearLayout layout = new android.widget.LinearLayout(requireContext());
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        int padding = (int) (16 * getResources().getDisplayMetrics().density);
        layout.setPadding(padding, padding, padding, padding);

        final android.widget.EditText input = new android.widget.EditText(requireContext());
        input.setHint("@username");
        input.setTextColor(android.graphics.Color.BLACK);
        input.setHintTextColor(android.graphics.Color.GRAY);
        input.setInputType(
                android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        layout.addView(input);

        final android.widget.TextView tvStatus = new android.widget.TextView(requireContext());
        tvStatus.setPadding(0, padding / 2, 0, padding / 2);
        tvStatus.setText("Start typing to search...");
        tvStatus.setTextColor(getResources().getColor(R.color.text_muted));
        layout.addView(tvStatus);

        // List for results
        final ListView resultsList = new ListView(requireContext());
        resultsList.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (200 * getResources().getDisplayMetrics().density))); // Max height approx
        layout.addView(resultsList);

        final ArrayAdapter<com.example.smartsplitter.data.User> searchAdapter = new ArrayAdapter<com.example.smartsplitter.data.User>(
                requireContext(), android.R.layout.simple_list_item_1, new ArrayList<>()) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                TextView t = v.findViewById(android.R.id.text1);
                t.setTextColor(android.graphics.Color.BLACK);
                com.example.smartsplitter.data.User user = getItem(position);
                t.setText(user.name + " (@" + user.username + ")");
                return v;
            }
        };
        resultsList.setAdapter(searchAdapter);

        builder.setView(layout);
        builder.setTitle("Add Member");
        builder.setNegativeButton("Cancel", null);

        android.app.AlertDialog dialog = builder.create();

        // Handle Selection
        resultsList.setOnItemClickListener((parent, view, position, id) -> {
            com.example.smartsplitter.data.User user = searchAdapter.getItem(position);
            if (user != null) {
                // Add this user
                viewModel.addMemberByUsername(user.username, (success, message) -> {
                    requireActivity().runOnUiThread(() -> {
                        android.widget.Toast.makeText(getContext(), message, android.widget.Toast.LENGTH_SHORT).show();
                        if (success)
                            dialog.dismiss();
                    });
                });
            }
        });

        // Search Logic
        input.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                // Ensure starts with @ logic visually
                if (query.startsWith("@")) {
                    query = query.substring(1);
                }

                if (query.length() > 0) {
                    viewModel.searchUsers(query, users -> {
                        requireActivity().runOnUiThread(() -> {
                            searchAdapter.clear();
                            if (users != null && !users.isEmpty()) {
                                searchAdapter.addAll(users);
                                tvStatus.setVisibility(View.GONE);
                            } else {
                                tvStatus.setText("No records found");
                                tvStatus.setVisibility(View.VISIBLE);
                            }
                        });
                    });
                } else {
                    searchAdapter.clear();
                    tvStatus.setText("Start typing to search...");
                    tvStatus.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
            }
        });

        dialog.show();
    }

    private static class MemberAdapter extends ArrayAdapter<Member> {
        private final com.example.smartsplitter.utils.SessionManager sessionManager;

        public MemberAdapter(Context context, List<Member> members) {
            super(context, 0, members);
            sessionManager = new com.example.smartsplitter.utils.SessionManager(context);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent,
                        false);
            }
            Member member = getItem(position);
            TextView text = convertView.findViewById(android.R.id.text1);

            String displayName = member.displayName;
            String currentName = sessionManager.getUserName();
            String currentUsername = sessionManager.getUsername();

            boolean isMe = false;
            if (currentUsername != null && !currentUsername.isEmpty() && member.username != null
                    && !member.username.isEmpty()) {
                isMe = currentUsername.equalsIgnoreCase(member.username);
            } else if (currentName != null) {
                isMe = currentName.equalsIgnoreCase(member.displayName);
            }

            if (isMe) {
                displayName += " (Me)";
            }

            text.setText("👤 " + displayName);
            return convertView;
        }
    }
}
