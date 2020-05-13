package com.example.coronakanditaten2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "Fragment StartPage";

    private Button btnLogin;
    private Button btnToRegister;
    private Button btnLoginToStartPage;
    boolean passwordCorrect;
    String Password;
    String Email;
    EditText textPassword;
    EditText textEmail;
    Datahandler datahandler;

    private TextView textViewGoToResetPassword;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnToRegister = (Button) view.findViewById(R.id.btnToRegister);
        btnToRegister.setOnClickListener(this);
        btnLoginToStartPage = (Button) view.findViewById(R.id.btnLoginToStartPage);
        btnLoginToStartPage.setOnClickListener(this);
        textEmail = (EditText) view.findViewById(R.id.username);
        textPassword = (EditText) view.findViewById(R.id.password);

        textViewGoToResetPassword = (TextView) view.findViewById(R.id.textViewGoToResetPassword);
        textViewGoToResetPassword.setOnTouchListener(this);

        datahandler=new Datahandler();

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch(v.getId()){
            case R.id.btnToRegister:
                intent = new Intent(getActivity(), RegisterActivity.class);
                break;
            case R.id.btnLoginToStartPage:
                intent = new Intent(getActivity(),MainActivity.class);
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
                                Toast.makeText(getContext(),"logged in", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                intent.putExtra("pass",Password);
                                intent.putExtra("ema",Email);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getContext(),"wrong password or email", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            Toast.makeText(getContext(),"error with server", Toast.LENGTH_SHORT).show();
                        }



                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                        Toast.makeText(getContext(),"failed to connect to server", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.textViewGoToResetPassword:
                ((LoginActivity) getActivity()).setViewPager(0);
                break;
        }
        return false;
    }
}
