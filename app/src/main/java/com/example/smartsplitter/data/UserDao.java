package com.example.smartsplitter.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Query("SELECT * FROM users WHERE email = :email")
    User getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE email = :email AND passwordHash = :passwordHash")
    User login(String email, String passwordHash);

    @Query("SELECT * FROM users WHERE username = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM users WHERE username LIKE :query || '%' LIMIT 20")
    java.util.List<User> searchUsers(String query);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM users WHERE email = :email")
    LiveData<User> getUserLiveData(String email);
}
