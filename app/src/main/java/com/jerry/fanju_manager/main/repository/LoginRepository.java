package com.jerry.fanju_manager.main.repository;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.jerry.fanju_manager.main.model.User;

import cn.leancloud.LCUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginRepository {
    private static LoginRepository instance;
    private User user;

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    public void login(LCUser lcUser) {
        user = new User(lcUser.getUsername(), lcUser.getPassword(), lcUser.getEmail(), lcUser.getMobilePhoneNumber());
    }

    public void logOut() {
        user = null;
    }

    public User getUser() {
        return user;
    }

}
