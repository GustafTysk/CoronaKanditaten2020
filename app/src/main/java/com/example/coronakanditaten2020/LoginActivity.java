package com.example.coronakanditaten2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnToRegister;
    private Button btnLoginToStartPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnToRegister = (Button) findViewById(R.id.btnToRegister);
        btnLoginToStartPage = (Button) findViewById(R.id.btnLoginToStartPage);
        btnToRegister.setOnClickListener(this);
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
