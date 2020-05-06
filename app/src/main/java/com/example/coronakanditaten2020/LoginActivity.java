package com.example.coronakanditaten2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private Button btnToRegister;
    private Button btnLoginToStartPage;

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
                Toast.makeText(this,"Logged In", Toast.LENGTH_SHORT);
                intent = new Intent(this,MainActivity.class);
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
