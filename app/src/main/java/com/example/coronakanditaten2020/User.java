package com.example.coronakanditaten2020;

import android.provider.ContactsContract;

public class User {
    private String username;
    private String email;
    int age;
    String gender;
    private String password;
    private String timestamp;

    public User() {
    }

    public User(String username, String email, int age, String gender, String password, String timestamp) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.password = password;
        this.timestamp=timestamp;
    }

    public String printInformation(){
        return ("Username: "+this.username + "\n" + "Email: " + this.email + "\n" + "Age: " + this.age + "\n" +"Gender: " + this.gender + "\n" + "Password: " + this.password+this.timestamp);
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
