package com.example.coronakanditaten2020;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;


import androidx.annotation.RequiresApi;



public class Credentials implements Parcelable {
    String Email;
    String Password;
    String encrypt;

    protected Credentials(Parcel in) {
        Email = in.readString();
        Password = in.readString();
        encrypt = in.readString();
    }

    public static final Creator<Credentials> CREATOR = new Creator<Credentials>() {
        @Override
        public Credentials createFromParcel(Parcel in) {
            return new Credentials(in);
        }

        @Override
        public Credentials[] newArray(int size) {
            return new Credentials[size];
        }
    };

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getEncrypt() {
        return encrypt;
    }

    Credentials(String email, String password){
        this.Email=email;
        this.Password=password;
        encrypt=Password;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Email);
        dest.writeString(Password);
        dest.writeString(encrypt);
    }
}
