package com.example.smartsplitter.auth;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.smartsplitter.data.AppDatabase;
import com.example.smartsplitter.data.User;
import com.example.smartsplitter.data.UserDao;
import com.example.smartsplitter.utils.PasswordUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuthRepository {
    private UserDao userDao;
    private ExecutorService executorService;

    public AuthRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<User> login(String email, String password) {
        MutableLiveData<User> loginResult = new MutableLiveData<>();
        executorService.execute(() -> {
            String hash = PasswordUtils.hashPassword(password);
            User user = userDao.login(email, hash);
            loginResult.postValue(user);
        });
        return loginResult;
    }

    public LiveData<Boolean> register(String name, String email, String password, String username) {
        MutableLiveData<Boolean> registerResult = new MutableLiveData<>();
        executorService.execute(() -> {
            try {
                // Check if user exists (email or username)
                if (userDao.getUserByEmail(email) != null || userDao.getUserByUsername(username) != null) {
                    registerResult.postValue(false); // User already exists
                    return;
                }

                String hash = PasswordUtils.hashPassword(password);
                User newUser = new User(email, name, hash, username);
                userDao.insertUser(newUser);
                registerResult.postValue(true);
            } catch (Exception e) {
                e.printStackTrace();
                registerResult.postValue(false);
            }
        });
        return registerResult;
    }
}
