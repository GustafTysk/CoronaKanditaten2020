package com.example.coronakanditaten2020;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ForumFragment extends Fragment implements View.OnClickListener {

    public Button postMessage;
    public EditText messageInput;
    public TextView messageShow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        postMessage = (Button) view.findViewById(R.id.postMessage);
        // postMessage.setVisibility(View.GONE);
        postMessage.setOnClickListener(this);
        messageInput = (EditText) view.findViewById(R.id.messageInput);
        messageShow = (TextView) view.findViewById(R.id.messageShow);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.postMessage:
                String message = messageInput.getText().toString();
                // messageShow.setText(message);
                messageShow.append(message + "\n");
                break;
        }
    }
}
