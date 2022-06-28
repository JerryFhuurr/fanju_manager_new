package com.jerry.fanju_manager.main.model;

import cn.leancloud.LCUser;

public class User {
    private String username;
    private String password;
    private String email;
    private boolean emailVerified;
    private int phoneNumber;

    public User(String username, String password, String email, int phone){
        this.username = username;
        this.password = password;
        this.email = email;
        this.emailVerified = false;
        this.phoneNumber = phone;
    }

    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
        this.emailVerified = false;
        this.phoneNumber = 0;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", emailVerified=" + emailVerified +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
