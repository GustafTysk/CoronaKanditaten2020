package com.example.coronakanditaten2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Fragment Register";
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private Boolean emailCorrect;
    private Boolean usernameCorrect;
    private Boolean passwordCorrect;
    private Boolean ageCorrect;
    private Boolean genderCorrect = false;

    private Button btnToLogin;
    private Button btnRegister;

    private String username;
    private String email;
    private int age;
    private String gender;
    private String password;

    private EditText textUsername;
    private EditText textEmail;
    private EditText textAge;
    private EditText textPassword;
    private RadioButton lastButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnToLogin = (Button) findViewById(R.id.btnToLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnToLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        textEmail = (EditText) findViewById(R.id.textEmail);
        textAge = (EditText) findViewById(R.id.textAge);
        textUsername = (EditText) findViewById(R.id.textUsername);
        textPassword = (EditText) findViewById(R.id.textPassword);
        lastButton = (RadioButton) findViewById(R.id.radio_other);

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
                username = textUsername.getText().toString();
                checkUsername(username);
                String ageString = (String) textAge.getText().toString();
                checkAge(ageString);

                password = textPassword.getText().toString();
                checkPassword(password);

                email = textEmail.getText().toString().trim();
                checkEmail(email);

                checkGender();


                if(emailCorrect && passwordCorrect && usernameCorrect && ageCorrect && genderCorrect){
                    User newUser = new User(username,email,age,gender,password);
                    Toast.makeText(getApplicationContext(), "Successfully created new user", Toast.LENGTH_LONG).show();
                    intent = new Intent(this, LoginActivity.class);
                    System.out.println(newUser.printInformation());
                    emailCorrect = false;
                    passwordCorrect = false;
                    usernameCorrect = false;
                }

                break;

            default:
        }
        if(intent != null){
            startActivity(intent);
        }
    }

    private void checkPassword(String password){
        if(password.length() >= 4){
            passwordCorrect = true;
        }
        else{
            passwordCorrect = false;
            textPassword.setError("Password must exceed 4 characters");
        }
    }

    private void checkUsername(String username){
        if(username.length() >= 1){
            usernameCorrect = true;
        }
        else{
            usernameCorrect = false;
            textUsername.setError("Username must exceed 1 character");
        }
    }

    private void checkEmail(String email){
        if (email.matches(emailPattern))
        {
            //Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
            emailCorrect = true;
        }
        else
        {
            textEmail.setError("Invalid email");
            emailCorrect = false;
        }
    }

    private void checkAge(String givenAge) {
        if (givenAge.length() > 0) {
            ageCorrect = true;
            age = Integer.valueOf(givenAge);

        }
        else {
            ageCorrect = false;
            textAge.setError("Please enter your age");

        }
    }

    public void onRadioButtonClicked(View view){

        boolean checked = ((RadioButton)view).isChecked();
            genderCorrect = true;
            switch (view.getId()) {
                case R.id.radio_male:
                    if (checked)
                        gender = "male";
                    break;
                case R.id.radio_female:
                    if (checked)
                        gender = "female";
                    break;
                case R.id.radio_other:
                    if (checked)
                        gender = "other";
                    break;
            }
            genderCorrect = true;

    }

    private void checkGender(){
        if(genderCorrect == false){
            lastButton.setError("Please choose gender");
        }
    }




}
