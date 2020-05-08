package com.example.coronakanditaten2020;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class ForumFragment extends Fragment implements View.OnClickListener {

    private Button postMessage;
    private Button btnForumToStart;

    private EditText messageInput;
    private EditText messageTitle;
    private TextView messageShow;
    private String currentCategory;

    public int id = 1;
    public int parentId = 0;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum, container, false);

        postMessage = (Button) view.findViewById(R.id.postMessage);
        postMessage.setOnClickListener(this);
        btnForumToStart = (Button) view.findViewById(R.id.btnForumToStart);
        btnForumToStart.setOnClickListener(this);
        messageInput = (EditText) view.findViewById(R.id.messageInput);
        messageTitle = (EditText) view.findViewById(R.id.messageTitle);
        messageShow = (TextView) view.findViewById(R.id.messageShow);

        return view;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_pirates:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_ninjas:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.postMessage:
                String username = "test";
                String title = messageTitle.getText().toString();
                String timestamp = "Timestamp";
                String message = messageInput.getText().toString();
                ArrayList<String> likes = new ArrayList<String>();
                likes.add("1");
                // messageShow.setText(message);
                // messageShow.append(title + "\n" + message + "\n");

                Post newPost = new Post(username, title, timestamp, message, likes, currentCategory, id, parentId);
                System.out.println(newPost.printInformation());
                break;
            case R.id.btnForumToStart:
                ((MainActivity) getActivity()).setViewPager(1);
                break;

        }
    }
}
