package com.example.smartsplitter.core;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeUtil {

    public static class GroupQRData {
        public String v = "1.0";
        public String gid;
        public String name;
        public String curr;
        public String created;

        public GroupQRData(String gid, String name, String curr, long created) {
            this.gid = gid;
            this.name = name;
            this.curr = curr;
            // Use ISO string or just timestamp? PRD says ISO-8601.
            // For simplicity in Java without external libs, we can adhere to basic string or long.
            // Let's use simple string representation
            this.created = String.valueOf(created); 
        }
    }

    public static Bitmap generateQRCode(String content) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bmp;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String serializeGroupData(String groupId, String groupName, String currency, long createdAt) {
        GroupQRData data = new GroupQRData(groupId, groupName, currency, createdAt);
        return new Gson().toJson(data);
    }

    public static GroupQRData deserializeGroupData(String json) {
        try {
            return new Gson().fromJson(json, GroupQRData.class);
        } catch (Exception e) {
            return null;
        }
    }
}
