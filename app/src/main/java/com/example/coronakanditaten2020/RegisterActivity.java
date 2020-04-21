package com.example.coronakanditaten2020;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Fragment Register";

    private Button btnToLogin;
    private Button btnRegister;

    private String username;
    private ContactsContract.CommonDataKinds.Email email;
    private int age;
    private String gender;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnToLogin = (Button) findViewById(R.id.btnToLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnToLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch(view.getId()){
            case R.id.btnToLogin:
                intent = new Intent(this, LoginActivity.class);
                break;
            case R.id.btnRegister:
                //GET INFORMATION AND CREATE NEW USER
                System.out.println("NEW USER CREATED");
                break;

            default:
        }
        if(intent != null){
            startActivity(intent);
        }
    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton)view).isChecked();

        switch (view.getId()){
            case R.id.radio_male:
                if(checked)
                    System.out.println("MALE");
                break;
            case R.id.radio_female:
                if(checked)
                    System.out.println("FEMALE");
                break;
            case R.id.radio_other:
                if(checked)
                    System.out.println("OTHER");
                break;
        }
    }


}
