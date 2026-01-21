package com.example.smartsplitter.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartsplitter.MainActivity;
import com.example.smartsplitter.R;
import com.example.smartsplitter.utils.SessionManager;

public class SignupFragment extends Fragment {

    private AuthRepository authRepository;
    private SessionManager sessionManager;
    private EditText etName, etEmail, etPassword, etUsername;
    private CheckBox cbTerms;
    private Button btnSignup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authRepository = new AuthRepository(requireActivity().getApplication());
        sessionManager = new SessionManager(requireContext());

        etName = view.findViewById(R.id.et_signup_name);
        etUsername = view.findViewById(R.id.et_signup_username);
        etEmail = view.findViewById(R.id.et_signup_email);
        etPassword = view.findViewById(R.id.et_signup_password);
        cbTerms = view.findViewById(R.id.cb_terms);
        btnSignup = view.findViewById(R.id.btn_signup);
        TextView tvGoToLogin = view.findViewById(R.id.tv_go_to_login);

        btnSignup.setOnClickListener(v -> register());

        tvGoToLogin.setOnClickListener(v -> {
            if (getActivity() instanceof AuthActivity) {
                ((AuthActivity) getActivity()).loadFragment(new LoginFragment());
            }
        });
    }

    private void register() {
        String name = etName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        boolean termsAccepted = cbTerms.isChecked();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(username)) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!termsAccepted) {
            Toast.makeText(getContext(), "Please agree to Terms & Conditions", Toast.LENGTH_SHORT).show();
            return;
        }

        btnSignup.setEnabled(false);
        authRepository.register(name, email, password, username).observe(getViewLifecycleOwner(), success -> {
            btnSignup.setEnabled(true);
            if (success) {
                // Auto login after register
                sessionManager.createLoginSession(email, name, username);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            } else {
                Toast.makeText(getContext(), "Registration failed. Email or Username taken.", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
