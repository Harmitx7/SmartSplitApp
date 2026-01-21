package com.example.smartsplitter.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartsplitter.MainActivity;
import com.example.smartsplitter.R;
import com.example.smartsplitter.auth.AuthActivity;
import com.example.smartsplitter.data.AppDatabase;
import com.example.smartsplitter.data.User;
import com.example.smartsplitter.utils.SessionManager;

import java.util.concurrent.Executors;

public class SettingsFragment extends Fragment {

    private SessionManager session;
    private AppDatabase db;
    private TextView tvName, tvEmail, tvInitials;
    private ImageView ivAvatar;
    private View btnEditPhoto;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        session = new SessionManager(requireContext());
        db = AppDatabase.getDatabase(requireContext());

        tvName = view.findViewById(R.id.tv_settings_name);
        tvEmail = view.findViewById(R.id.tv_settings_email);
        ivAvatar = view.findViewById(R.id.iv_settings_avatar);
        tvInitials = view.findViewById(R.id.tv_settings_initials);
        btnEditPhoto = view.findViewById(R.id.btn_settings_edit_photo);

        loadUserProfile();

        // 1. Profile Edit Logic (Card Click)
        view.findViewById(R.id.card_settings_profile).setOnClickListener(v -> {
            showProfileEdit();
        });

        // Edit Photo Button Click
        btnEditPhoto.setOnClickListener(v -> {
            showProfileEdit();
        });

        // ... rest of listeners
        view.findViewById(R.id.btn_app_settings).setOnClickListener(v -> {
            Toast.makeText(getContext(), "App Settings Coming Soon", Toast.LENGTH_SHORT).show();
        });

        view.findViewById(R.id.btn_terms).setOnClickListener(v -> {
            showTermsDialog();
        });

        view.findViewById(R.id.btn_about).setOnClickListener(v -> {
            showAboutDialog();
        });

        view.findViewById(R.id.btn_logout).setOnClickListener(v -> {
            session.logoutUser();
            android.widget.Toast.makeText(getContext(), "Logged out", android.widget.Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), AuthActivity.class);
            startActivity(intent);
            if (getActivity() != null)
                getActivity().finish();
        });

        view.findViewById(R.id.btn_clear_data).setOnClickListener(v -> {
            // Show Password Confirmation Dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Clear All Data");
            builder.setMessage("This will wipe all data. Enter your password to confirm:");

            // Set up the input
            final android.widget.EditText input = new android.widget.EditText(getContext());
            input.setInputType(
                    android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("Clear", (dialog, which) -> {
                String password = input.getText().toString();
                if (!password.isEmpty()) {
                    verifyAndClearData(password);
                } else {
                    Toast.makeText(getContext(), "Password required", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });
    }

    private void verifyAndClearData(String password) {
        String email = session.getUserEmail();
        Executors.newSingleThreadExecutor().execute(() -> {
            User user = db.userDao().getUserByEmail(email);
            if (user != null) {
                String hashedInput = com.example.smartsplitter.utils.PasswordUtils.hashPassword(password);
                // Check against stored hash (Real User) OR plain text (Dummy User
                // compatibility)
                if (user.passwordHash.equals(hashedInput) || user.passwordHash.equals(password)) {
                    db.clearAllTables();
                    session.logoutUser();
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "All Data Cleared", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), AuthActivity.class);
                        startActivity(intent);
                        if (getActivity() != null)
                            getActivity().finish();
                    });
                } else {
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    private void showProfileEdit() {
        new ProfileEditBottomSheet().show(getParentFragmentManager(), "profile_edit");
    }

    private void loadUserProfile() {
        String name = session.getUserName();
        tvName.setText(name);
        tvEmail.setText(session.getUserEmail());

        // Set Initials
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

        Executors.newSingleThreadExecutor().execute(() -> {
            User user = db.userDao().getUserByEmail(session.getUserEmail());
            requireActivity().runOnUiThread(() -> {
                if (user != null && user.profileImageUri != null && !user.profileImageUri.isEmpty()) {
                    try {
                        ivAvatar.setImageURI(android.net.Uri.parse(user.profileImageUri));
                        ivAvatar.setVisibility(View.VISIBLE);
                        tvInitials.setVisibility(View.GONE);
                    } catch (Exception e) {
                        ivAvatar.setVisibility(View.GONE);
                        tvInitials.setVisibility(View.VISIBLE);
                    }
                } else {
                    ivAvatar.setVisibility(View.GONE);
                    tvInitials.setVisibility(View.VISIBLE);
                }
            });
        });
    }

    private void showTermsDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Terms and Conditions")
                .setMessage("1. Use this app to split expenses fairly.\n\n" +
                        "2. We are not responsible for broken friendships due to debts.\n\n" +
                        "3. Data is stored locally on your device.\n\n" +
                        "4. By using this app, you agree to be awesome.")
                .setPositiveButton("Close", null)
                .show();
    }

    private void showAboutDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("About Smart Split")
                .setMessage("Smart Split v1.0\n\n" +
                        "For: Jenil Revaliya\n\n" +
                        "A premium expense splitting application.")
                .setPositiveButton("Cool", null)
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh profile in case it was edited in the dialog
        loadUserProfile();
    }
}
