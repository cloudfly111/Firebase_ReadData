package com.example.firebase_readdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String,String> ToMap(){

        Map<String, String> map = new HashMap<String, String>();
        map.put("user",user);
        map.put("password",password);
        map.put("email",email);
        map.put("birthday",birthday);

        return map;
    }
}
