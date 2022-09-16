package com.example.firebase_readdata;

public class User {
    private String user;
    private String password;
    private String email;
    private String birthday;

    User() {
    }

    User(String user, String password, String email, String birthday) {
        this.user = user;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return birthday;
    }
}
