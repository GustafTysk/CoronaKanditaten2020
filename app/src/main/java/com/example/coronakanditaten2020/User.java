package com.example.coronakanditaten2020;

import android.provider.ContactsContract;

public class User {
    private String username;
    private ContactsContract.CommonDataKinds.Email email;
    private int age;
    private String gender;
    private String password;

    public User(String username, ContactsContract.CommonDataKinds.Email email, int age, String gender, String password) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.password = password;
    }

}
