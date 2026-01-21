package com.example.smartsplitter.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartsplitter.R;

public class CreateGroupFragment extends Fragment {

    private GroupViewModel viewModel;
    private EditText editName, editCreator, editCurrency, editDescription;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_group, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        editName = view.findViewById(R.id.et_group_name);
        editCreator = view.findViewById(R.id.et_creator_name);
        editCurrency = view.findViewById(R.id.et_currency);
        editDescription = view.findViewById(R.id.et_description);
        Button btnCreate = view.findViewById(R.id.btn_create_group);

        View btnBack = view.findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        }

        com.example.smartsplitter.utils.SessionManager sessionManager = new com.example.smartsplitter.utils.SessionManager(
                requireContext());
        String currentName = sessionManager.getUserName();
        if (currentName != null) {
            editCreator.setText(currentName);
        }

        // Setup Currency Dropdown
        android.widget.AutoCompleteTextView currencyDropdown = (android.widget.AutoCompleteTextView) editCurrency;
        String[] currencies = { "INR", "USD", "EUR", "GBP", "CAD", "AUD", "JPY", "CNY" };
        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(
                requireContext(),
                R.layout.item_dropdown,
                currencies);
        currencyDropdown.setAdapter(adapter);
        currencyDropdown.setText("INR", false); // Default to INR

        btnCreate.setOnClickListener(v -> {
            String name = editName.getText().toString();
            String creator = editCreator.getText().toString();
            String currency = editCurrency.getText().toString();
            String desc = editDescription.getText().toString();

            if (name.isEmpty() || creator.isEmpty() || currency.isEmpty()) {
                android.widget.Toast
                        .makeText(getContext(), "Please fill required fields", android.widget.Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            // Always use the latest session data for the username link
            String currentUsername = sessionManager.getUsername();

            viewModel.createGroup(name, currency, desc, creator, currentUsername);
            android.widget.Toast.makeText(getContext(), "Group Created Successfully", android.widget.Toast.LENGTH_SHORT)
                    .show();
            getParentFragmentManager().popBackStack();
        });
    }
}
