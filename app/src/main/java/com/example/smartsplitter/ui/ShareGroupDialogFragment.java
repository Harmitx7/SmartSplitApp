package com.example.smartsplitter.ui;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.smartsplitter.R;
import com.example.smartsplitter.core.QRCodeUtil;
import com.example.smartsplitter.data.Group;

public class ShareGroupDialogFragment extends DialogFragment {

    private static final String ARG_GROUP_ID = "gid";
    private static final String ARG_GROUP_NAME = "name";
    private static final String ARG_CURRENCY = "curr";
    private static final String ARG_CREATED = "created";

    public static ShareGroupDialogFragment newInstance(Group group) {
        ShareGroupDialogFragment frag = new ShareGroupDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GROUP_ID, group.groupId);
        args.putString(ARG_GROUP_NAME, group.groupName);
        args.putString(ARG_CURRENCY, group.currency);
        args.putLong(ARG_CREATED, group.createdAt);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.Theme_SmartSplitter);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_share_qr, null);

        ImageView ivQr = view.findViewById(R.id.iv_qr_code);
        TextView tvGroupName = view.findViewById(R.id.tv_group_name);

        if (getArguments() != null) {
            String gid = getArguments().getString(ARG_GROUP_ID);
            String name = getArguments().getString(ARG_GROUP_NAME);
            String curr = getArguments().getString(ARG_CURRENCY);
            long created = getArguments().getLong(ARG_CREATED);

            String json = QRCodeUtil.serializeGroupData(gid, name, curr, created);
            Bitmap bitmap = QRCodeUtil.generateQRCode(json);
            
            if (bitmap != null) {
                ivQr.setImageBitmap(bitmap);
            }
            tvGroupName.setText(name);
        }

        builder.setView(view);

        return builder.create();
    }
}
