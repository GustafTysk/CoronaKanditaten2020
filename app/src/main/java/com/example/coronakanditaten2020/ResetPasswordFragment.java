package com.example.coronakanditaten2020;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResetPasswordFragment extends Fragment implements View.OnClickListener {
    private EditText textEmail;
    private String EmailString;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Button btnVerifyEmailAndSendCode;
    private EditText verificationCodeView;
    private String verificationCode;
    private EditText newPasswordView;
    private String newPassword;
    private Button btnChangePassword;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        btnVerifyEmailAndSendCode =(Button) view.findViewById(R.id.btnVerifyEmailAndSendCode);
        btnVerifyEmailAndSendCode.setOnClickListener(this);

        verificationCodeView = (EditText) view.findViewById(R.id.verificationCodeCheck);
        newPasswordView = (EditText) view.findViewById(R.id.newPassword);

        btnChangePassword = (Button) view.findViewById(R.id.btnResetPassword);
        btnChangePassword.setOnClickListener(this);

        textEmail = (EditText) view.findViewById(R.id.emailToConfirm);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnVerifyEmailAndSendCode:
                EmailString = textEmail.getText().toString();
                if (checkEmail(EmailString)) {
            Call<Boolean> SendPasswordCode = ((LoginActivity) getActivity()).datahandler.clientAPI.verifypassword(EmailString);
            SendPasswordCode.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(getContext(),getString(R.string.error_with_server), Toast.LENGTH_SHORT).show();
                        btnVerifyEmailAndSendCode.setText(R.string.btn_resend_code);


                    }
                    else if(response.body()) {
                        Toast.makeText(getContext(),getString(R.string.sent_email_toast), Toast.LENGTH_SHORT).show();

                        showChangePasswordFields();
                        btnVerifyEmailAndSendCode.setText(R.string.Resend_code);

                    }
                     else {
                        Toast.makeText(getContext(),getString(R.string.wrong_email), Toast.LENGTH_SHORT).show();
                        textEmail.setError(getString(R.string.invalid_email));

                     }

                }

                 @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                         Toast.makeText(getContext(),getString(R.string.fail_connect_to_server), Toast.LENGTH_SHORT).show();

                    }
                 });}

                else{
                    textEmail.setError(getString(R.string.invalid_email));

                }
                break;
            case R.id.btnResetPassword:
                System.out.println("button clicked");
                    newPassword = newPasswordView.getText().toString();
                    if (passwordOK(newPassword, newPasswordView)){
                        System.out.println("password checked");
                        changeUserPassword();
                    }
                    else{

                    }

                break;

    }
    }

    private boolean checkEmail(String email){
        if (email.matches(emailPattern))
        {
            //Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
          return true;
        }
        else
        {
            textEmail.setError(getString(R.string.invalid_email));
            return false;
        }
    }

    public void showChangePasswordFields(){
        verificationCodeView.setVisibility(View.VISIBLE);
        newPasswordView.setVisibility(View.VISIBLE);
        btnChangePassword.setVisibility(View.VISIBLE);
    }



    public boolean passwordOK(String password, EditText editText){
        if(password.length() >= 4){
            return true;
        }
        else{
            editText.setError(getString(R.string.invalid_password));
        }
        return false;
    }

    public void changeUserPassword(){
        System.out.println(EmailString+verificationCodeView.getText().toString()+newPassword);
        Call<Boolean> changepassword= ((LoginActivity) getActivity()).datahandler.clientAPI.setpassword(EmailString,verificationCodeView.getText().toString(),newPassword);
        changepassword.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),getString(R.string.error_with_server), Toast.LENGTH_SHORT).show();
                }
                else if(!response.body()){
                    verificationCodeView.setError(getString(R.string.error_wrong_code));
                }
                else{
                    Toast.makeText(getContext(),getString(R.string.page_name_reset_password), Toast.LENGTH_SHORT).show();
                    ((LoginActivity) getActivity()).setViewPager(1);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println(t);
            }
        });

    }

}
