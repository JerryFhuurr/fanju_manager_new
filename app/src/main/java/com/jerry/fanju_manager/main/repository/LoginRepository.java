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

    public void login(String username, String password, String email) {
        if (username != null && email == null) {
            LCUser.logIn(username, password).subscribe(new Observer<LCUser>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(LCUser lcUser) {
                    Log.i(TAG, "Login Successfully!");
                    user = new User(lcUser.getUsername(), lcUser.getPassword(), lcUser.getEmail(), lcUser.getMobilePhoneNumber());
                }

                @Override
                public void onError(Throwable e) {
                    Log.i(TAG, e.toString());
                }

                @Override
                public void onComplete() {

                }
            });
        } else if (username == null && email != null){
            LCUser.loginByEmail(email, password).subscribe(new Observer<LCUser>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(LCUser lcUser) {
                    Log.i(TAG, "Login Successfully!");
                    user = new User(lcUser.getUsername(), lcUser.getPassword(), lcUser.getEmail(), lcUser.getMobilePhoneNumber());
                }

                @Override
                public void onError(Throwable e) {
                    Log.i(TAG, e.toString());
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    public void logOut(){
        LCUser.logOut();
        user = null;
    }

    public User getUser(){
        return user;
    }
}
