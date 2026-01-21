package com.example.smartsplitter.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartsplitter.R;
import com.example.smartsplitter.core.QRCodeUtil;
import com.example.smartsplitter.data.Group;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class JoinGroupFragment extends Fragment {

    private GroupViewModel viewModel;
    private QRCodeUtil.GroupQRData scannedData;
    private TextView tvScannedGroupName;
    private EditText editName;
    private Button btnJoin;
    private LinearLayout layoutGroupInfo;

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    processQrContent(result.getContents());
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_join_group, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        tvScannedGroupName = view.findViewById(R.id.tv_scanned_group_name);
        editName = view.findViewById(R.id.et_display_name);
        btnJoin = view.findViewById(R.id.btn_join_group);
        layoutGroupInfo = view.findViewById(R.id.layout_group_info);
        Button btnScan = view.findViewById(R.id.btn_scan_qr);

        btnScan.setOnClickListener(v -> {
            ScanOptions options = new ScanOptions();
            options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
            options.setPrompt("Scan a group QR code");
            options.setCameraId(0); 
            options.setBeepEnabled(false);
            options.setBarcodeImageEnabled(true);
            barcodeLauncher.launch(options);
        });

        btnJoin.setOnClickListener(v -> {
            if (scannedData != null && !editName.getText().toString().isEmpty()) {
                long createdAt = 0;
                try {
                    createdAt = Long.parseLong(scannedData.created);
                } catch (NumberFormatException e) {
                    createdAt = System.currentTimeMillis();
                }

                Group group = new Group(scannedData.name, scannedData.curr, "");
                group.groupId = scannedData.gid; 
                group.createdAt = createdAt;
                
                viewModel.joinGroup(group, editName.getText().toString());
                getParentFragmentManager().popBackStack();
            } else {
                Toast.makeText(getContext(), "Please scan a code and enter your name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processQrContent(String content) {
        scannedData = QRCodeUtil.deserializeGroupData(content);
        if (scannedData != null) {
            tvScannedGroupName.setText(scannedData.name);
            layoutGroupInfo.setVisibility(View.VISIBLE);
            btnJoin.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getContext(), "Invalid Group QR Code", Toast.LENGTH_SHORT).show();
            layoutGroupInfo.setVisibility(View.GONE);
            btnJoin.setVisibility(View.GONE);
        }
    }
}
