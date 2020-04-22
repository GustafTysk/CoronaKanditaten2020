package com.example.coronakanditaten2020;

import android.provider.ContactsContract;

public class User {
    private String username;
    private String email;
    private int age;
    private String gender;
    private String password;

    public User(String username, String email, int age, String gender, String password) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.password = password;
    }

    public String printInformation(){
        return ("Username: "+this.username + "\n" + "Email: " + this.email + "\n" + "Age: " + this.age + "\n" +"Gender: " + this.gender + "\n" + "Password: " + this.password);
    };

}
