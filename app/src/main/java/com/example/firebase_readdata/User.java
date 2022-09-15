package com.example.firebase_readdata;

public class User {
    private String user;
    private String password;
    private Long age;
    private String birthday;

    User() {
    }

    User(String user, String password, Long age, String birthday) {
        this.user = user;
        this.password = password;
        this.age = age;
        this.birthday = birthday;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Long getAge() {
        return age;
    }

    public String getBirthday() {
        return birthday;
    }
}
