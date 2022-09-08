package com.example.firebase_readdata;

public class Format {
    private String id;
    private String value;

    private Format(){}

    public Format(String id,String value){
        this.id=id;
        this.value=value;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

 }
