package com.jerry.fanju_manager.main.viewmodel;

import androidx.lifecycle.ViewModel;

import com.jerry.fanju_manager.main.model.User;
import com.jerry.fanju_manager.main.repository.LoginRepository;

public class LoginViewModel extends ViewModel {
    private LoginRepository loginRepository;

    public LoginViewModel() {
        loginRepository = LoginRepository.getInstance();
    }

    public void login(String username, String password, String email) {
        loginRepository.login(username, password, email);
    }

    public User getCurrentUser() {
        return loginRepository.getUser();
    }

    public void logOut(){
        loginRepository.logOut();
    }
}
