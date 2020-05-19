package com.example.coronakanditaten2020;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.Calendar;
import java.util.Date;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Fragment Register";
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private Boolean emailCorrect;
    private Boolean usernameCorrect;
    private Boolean passwordCorrect;
    private Boolean ageCorrect;
    private Boolean genderCorrect = false;
    private Boolean emailVerified = false;

    private Button btnToLogin;
    private Button btnRegister;

    private String username;
    private String email;
    private int age;
    private String gender;
    private String password;
    private String timstamp;
    private String verificationCode;

    private EditText textUsername;
    private EditText textEmail;
    private TextView textAge;
    private EditText textPassword;
    private RadioButton lastButton;
    Datahandler datahandler = new Datahandler();

    //Datepicker calendar
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private String textViewDate;
    private Calendar cal;
    private Calendar today;
    private Calendar dob;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnToLogin = (Button) findViewById(R.id.btnToLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnToLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        textEmail = (EditText) findViewById(R.id.textEmail);
        textAge = (TextView) findViewById(R.id.textAge);
        textUsername = (EditText) findViewById(R.id.textUsername);
        textPassword = (EditText) findViewById(R.id.textPassword);
        lastButton = (RadioButton) findViewById(R.id.radio_other);

        textAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this,
                        R.style.Theme_AppCompat_DayNight_Dialog_MinWidth,
                        onDateSetListener,year, month, day);
                dialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
                dialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);
                textViewDate = year + "/" + month + "/" + day;
                textAge.setText(textViewDate);
                calculateAge(cal.getTimeInMillis());
            }
        };
    }


    int calculateAge(long date){
        dob = Calendar.getInstance();
        dob.setTimeInMillis(date);
        today = Calendar.getInstance();
        age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if(today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
            age--;
        }
        System.out.println("Age for selected date: " + age);
        return age;
    }


    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch(view.getId()){
            case R.id.btnToLogin:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btnRegister:
                //GET INFORMATION AND CREATE NEW USER
                username = textUsername.getText().toString();
                checkUsername(username);
                String ageString = "" + textAge.getText();
                checkAge(ageString);

                password = textPassword.getText().toString();
                checkPassword(password);

                checkGender();

                email = textEmail.getText().toString().trim();
                checkEmail(email);

                if(emailCorrect) {
                    sendVerificationCodeToEmail();
                    verifyEmailDialog(); // Verifies email
                }


                if(emailCorrect && passwordCorrect && usernameCorrect && ageCorrect && genderCorrect && emailVerified){
                    Date date = new Date();
                    timstamp=date.toString();
                    System.out.println(timstamp);
                    User newUser = new User(username,email,age,gender,password,timstamp);
                    sendVerificationCodeToEmail();
                    verifyEmailDialog();
                    Call<Boolean> createuser = datahandler.clientAPI.createuser(newUser);
                    createuser.enqueue(new Callback<Boolean>() {


                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if(!response.isSuccessful() || !response.body()){
                               Toast.makeText(getApplicationContext(), getString(R.string.fail_create_user), Toast.LENGTH_LONG).show();
                                System.out.println(response.toString());
                            }
                            else{
                                Toast.makeText(getApplicationContext(), getString(R.string.success_create_user), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                System.out.println(newUser.printInformation());
                                emailCorrect = false;
                                passwordCorrect = false;
                                usernameCorrect = false;
                                startActivity(intent);

                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), getString(R.string.fail_connect_to_server), Toast.LENGTH_LONG).show();

                        }
                    });
                }

                break;

            default:
        }
    }

    private void checkPassword(String password){
        if(password.length() >= 4){
            passwordCorrect = true;
        }
        else{
            passwordCorrect = false;
            textPassword.setError(getString(R.string.invalid_password));
        }
    }

    private void checkUsername(String username){
        if(username.length() >= 1){
            usernameCorrect = true;
        }
        else{
            usernameCorrect = false;
            textUsername.setError(getString(R.string.invalid_username));
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
            textEmail.setError(getString(R.string.invalid_email));
            emailCorrect = false;
        }
    }

    private void checkAge(String givenAge) {
        if (givenAge.length() > 0) {
            ageCorrect = true;
        }
        else {
            ageCorrect = false;
            textAge.setError(getString(R.string.invalid_age));

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
            lastButton.setError(getString(R.string.invalid_gender));
        }
    }

    public void sendVerificationCodeToEmail(){

        Call<Boolean> veremail=datahandler.clientAPI.verifyemail(email);
        veremail.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), getString(R.string.error_with_server), Toast.LENGTH_LONG).show();
                    System.out.println(response);
                }
                else if(response.body()==false){
                    Toast.makeText(getApplicationContext(), "failed to send email", Toast.LENGTH_LONG).show();
                }
                else{
                    verifyEmailDialog();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getString(R.string.fail_connect_to_server), Toast.LENGTH_LONG).show();

            }
        });

    }

    public void verifyEmailDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_verify_email);
        dialog.setTitle(getString(R.string.verify_email));
        dialog.setCancelable(true);

        TextView textView = dialog.findViewById(R.id.infoAndResend);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCodeToEmail();
            }
        });
        Button buttonVerify = (Button) dialog.findViewById(R.id.Accept);
        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText EditText = dialog.findViewById(R.id.verificationEditText);

                String EditTextValue = EditText.getText().toString();
                Date date = new Date();
                timstamp=date.toString();
                System.out.println(timstamp);
                User newUser = new User(username,email,age,gender,password,timstamp);
                Call<Boolean> createuser = datahandler.clientAPI.createuser(newUser);
                createuser.enqueue(new Callback<Boolean>() {


                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(!response.isSuccessful() || !response.body()){
                            Toast.makeText(getApplicationContext(), getString(R.string.fail_create_user), Toast.LENGTH_LONG).show();
                            System.out.println(response.toString());
                        }
                        else if(!response.body()) {
                            EditText.setError(getString(R.string.error_wrong_code));
                        }
                        else{
                            Toast.makeText(getApplicationContext(), getString(R.string.success_create_user), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            System.out.println(newUser.printInformation());
                            emailCorrect = false;
                            passwordCorrect = false;
                            usernameCorrect = false;
                            startActivity(intent);


                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), getString(R.string.fail_connect_to_server), Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
        Button buttonCancel = (Button) dialog.findViewById(R.id.Cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }








    }



