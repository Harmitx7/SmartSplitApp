package com.example.smartsplitter.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "groups")
public class Group {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "group_id")
    public String groupId;

    @NonNull
    @ColumnInfo(name = "group_name")
    public String groupName;

    @NonNull
    @ColumnInfo(name = "currency")
    public String currency;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "created_at")
    public long createdAt;

    @ColumnInfo(name = "updated_at")
    public long updatedAt;

    @ColumnInfo(name = "is_archived")
    public boolean isArchived;

    @NonNull
    @ColumnInfo(name = "qr_code_data")
    public String qrCodeData;

    @Ignore
    public Group(@NonNull String groupName, @NonNull String currency, String description) {
        this.groupId = UUID.randomUUID().toString();
        this.groupName = groupName;
        this.currency = currency;
        this.description = description;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.isArchived = false;
        this.qrCodeData = ""; // Generated later
    }
    
    // Empty constructor for Room
    public Group() {}
}
