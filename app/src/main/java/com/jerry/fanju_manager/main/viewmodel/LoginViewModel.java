package com.jerry.fanju_manager.main.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.jerry.fanju_manager.main.model.User;
import com.jerry.fanju_manager.main.repository.LoginRepository;

import cn.leancloud.LCUser;

public class LoginViewModel extends ViewModel {
    private LoginRepository loginRepository;

    public LoginViewModel() {
        loginRepository = LoginRepository.getInstance();
    }

    public void login(LCUser lcUser) {
        loginRepository.login(lcUser);
    }

    public User getCurrentUser() {
        return loginRepository.getUser();
    }

    public void logOut(){
        loginRepository.logOut();
    }

}
