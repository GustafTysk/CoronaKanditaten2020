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
    private Button btnConfirmEmailAndSendCode;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        btnConfirmEmailAndSendCode=(Button) view.findViewById(R.id.btnConfirmEmailAndSendCode);
        btnConfirmEmailAndSendCode.setOnClickListener(this);

        textEmail = (EditText) view.findViewById(R.id.emailToConfirm);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnConfirmEmailAndSendCode:
                EmailString=textEmail.getText().toString();
                if (checkEmail(EmailString)) {
            Call<Boolean> SendPasswordCode = ((LoginActivity) getActivity()).datahandler.clientAPI.verifypassword(EmailString);
            SendPasswordCode.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(getContext(),getString(R.string.error_with_server), Toast.LENGTH_SHORT).show();
                        btnConfirmEmailAndSendCode.setText(R.string.btn_resend_code);


                    }
                    else if(response.body()) {
                        Toast.makeText(getContext(),getString(R.string.sent_email_toast), Toast.LENGTH_SHORT).show();

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

}
