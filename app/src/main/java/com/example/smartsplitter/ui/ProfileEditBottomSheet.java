package com.example.smartsplitter.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.smartsplitter.R;
import com.example.smartsplitter.data.AppDatabase;
import com.example.smartsplitter.data.User;
import com.example.smartsplitter.utils.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.concurrent.Executors;

public class ProfileEditBottomSheet extends BottomSheetDialogFragment {

    private EditText etName;
    private TextView tvEmail, tvInitials;
    private ImageView ivAvatar;
    private Button btnSave;
    private String currentImageUri = null;
    private User currentUser;

    // Photo Picker
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_profile_edit, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                currentImageUri = uri.toString();
                ivAvatar.setImageURI(uri);
                ivAvatar.setVisibility(View.VISIBLE);
                tvInitials.setVisibility(View.GONE);

                // Persist permission (optional but recommended for URIs)
                try {
                    requireContext().getContentResolver().takePersistableUriPermission(uri,
                            android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etName = view.findViewById(R.id.et_profile_name);
        tvEmail = view.findViewById(R.id.tv_profile_email);
        ivAvatar = view.findViewById(R.id.iv_profile_avatar_edit);
        tvInitials = view.findViewById(R.id.tv_profile_initials_edit);
        btnSave = view.findViewById(R.id.btn_save_profile);
        View btnChangePhoto = view.findViewById(R.id.btn_change_photo);

        SessionManager session = new SessionManager(requireContext());
        String email = session.getUserEmail();
        tvEmail.setText(email);

        String currentName = session.getUserName();
        etName.setText(currentName);

        // Set Initials
        if (currentName != null && !currentName.isEmpty()) {
            String[] parts = currentName.trim().split("\\s+");
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

        // Load current data
        AppDatabase db = AppDatabase.getDatabase(requireContext());
        Executors.newSingleThreadExecutor().execute(() -> {
            currentUser = db.userDao().getUserByEmail(email);
            requireActivity().runOnUiThread(() -> {
                if (currentUser != null && currentUser.profileImageUri != null
                        && !currentUser.profileImageUri.isEmpty()) {
                    currentImageUri = currentUser.profileImageUri;
                    try {
                        ivAvatar.setImageURI(android.net.Uri.parse(currentImageUri));
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

        btnChangePhoto.setOnClickListener(v -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        btnSave.setOnClickListener(v -> {
            String newName = etName.getText().toString().trim();
            if (newName.isEmpty()) {
                etName.setError("Name required");
                return;
            }

            Executors.newSingleThreadExecutor().execute(() -> {
                if (currentUser != null) {
                    currentUser.name = newName;
                    currentUser.profileImageUri = currentImageUri;
                    db.userDao().updateUser(currentUser);

                    // Update Session
                    session.createLoginSession(email, newName); // Updates name in pref

                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                        dismiss();
                        // Ideally refresh parent fragment, but standard navigation flow handles it on
                        // restart or via LiveData
                        // For now, user has to navigate or we can trigger a recreate if needed.
                        // Actually, GroupListFragment does "setupProfileHeader" in onViewCreated.
                        // If we dismiss, the underlying fragment view is still there.
                        // We might want to notify it. But for MVP, this is enough.
                    });
                }
            });
        });
    }
}
