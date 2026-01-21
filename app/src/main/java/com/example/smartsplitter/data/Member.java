package com.example.smartsplitter.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "members", foreignKeys = @ForeignKey(entity = Group.class, parentColumns = "group_id", childColumns = "group_id", onDelete = ForeignKey.CASCADE), indices = {
        @Index(value = { "group_id", "display_name" }, unique = true) })
public class Member {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "member_id")
    public String memberId;

    @NonNull
    @ColumnInfo(name = "group_id")
    public String groupId;

    @NonNull
    @ColumnInfo(name = "display_name")
    public String displayName;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "created_at")
    public long createdAt;

    @Ignore
    public Member(@NonNull String groupId, @NonNull String displayName) {
        this.memberId = UUID.randomUUID().toString();
        this.groupId = groupId;
        this.displayName = displayName;
        this.createdAt = System.currentTimeMillis();
    }

    // Empty constructor for Room
    public Member() {
    }
}
