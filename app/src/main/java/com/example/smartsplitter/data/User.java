package com.example.smartsplitter.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users", indices = { @androidx.room.Index(value = "username", unique = true) })
public class User {
    @PrimaryKey
    @NonNull
    public String email; // Using email as ID for simplicity

    public String username;
    public String name;
    public String passwordHash;
    public String phoneNumber;
    public String profileImageUri; // Optional: for future avatar support

    public User(@NonNull String email, String name, String passwordHash, String username) {
        this.email = email;
        this.name = name;
        this.passwordHash = passwordHash;
        this.username = username;
    }

    @androidx.room.Ignore
    public User() {
    }
}
