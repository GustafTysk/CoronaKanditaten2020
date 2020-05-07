package com.example.coronakanditaten2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private Button btnToRegister;
    private Button btnLoginToStartPage;
    boolean passwordCorrect;
    String Password;
    String Email;
    EditText textPassword;
    EditText textEmail;
    Datahandler datahandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnToRegister = (Button) findViewById(R.id.btnToRegister);
        btnToRegister.setOnClickListener(this);
        btnLoginToStartPage = (Button) findViewById(R.id.btnLoginToStartPage);
        btnLoginToStartPage.setOnClickListener(this);
        textEmail = (EditText) findViewById(R.id.username);
        textPassword = (EditText) findViewById(R.id.password);
        datahandler=new Datahandler();
    }


    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch(view.getId()){
            case R.id.btnToRegister:
                intent = new Intent(this, RegisterActivity.class);
                break;
            case R.id.btnLoginToStartPage:
                intent = new Intent(this,MainActivity.class);
                break;
            case R.id.btnLogin:
                Email=textEmail.getText().toString();
                Password=textPassword.getText().toString();
                datahandler.credentials=new Credentials(Email,Password);
                Call<Boolean> login=datahandler.clientAPI.login(datahandler.credentials.encrypt,datahandler.credentials.Email,datahandler.credentials.Password);
                login.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful()){
                            if (response.body()==true){
                                Toast.makeText(getApplicationContext(),"logged in", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("pass",Password);
                                intent.putExtra("ema",Email);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"wrong password or email", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"error with server", Toast.LENGTH_SHORT).show();
                        }



                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                        Toast.makeText(getApplicationContext(),"failed to connect to server", Toast.LENGTH_SHORT).show();
                    }
                });



                break;
//            case R.id.btnNextMenu:
//                intent = new Intent(this, PlayMenu.class);
//                break;

            default:
        }
        if(intent != null){
            startActivity(intent);
        }
    }



}
