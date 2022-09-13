package com.example.firebase_readdata;

public class User {
    private String user;
    private String password;
    private Integer age;
    User(){}

    User(String user,String password,Integer age){
        this.user=user;
        this.password=password;
        this.age=age;
    }

    public String getUser() {
        return user;
    }
    public String getPassword() {
        return password;
    }

    public Integer getAge() {
        return age;
    }
}
