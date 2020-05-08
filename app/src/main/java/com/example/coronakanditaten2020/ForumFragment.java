package com.example.coronakanditaten2020;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class ForumFragment extends Fragment implements View.OnClickListener {

    private Button messageButton;
    private Button btnForumToStart;

    private EditText messageInput;
    private EditText messageTitle;
    private TextView messageShow;
    private String currentCategory;

    public ListView listView;

    public int id = 1;
    public int parentId = 0;

    public ArrayList<Post>postList;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum, container, false);

        messageButton = (Button) view.findViewById(R.id.messageButton);
        messageButton.setOnClickListener(this);
        btnForumToStart = (Button) view.findViewById(R.id.btnForumToStart);
        btnForumToStart.setOnClickListener(this);
        messageInput = (EditText) view.findViewById(R.id.messageInput);
        messageTitle = (EditText) view.findViewById(R.id.messageTitle);
        messageShow = (TextView) view.findViewById(R.id.messageShow);

        listView = (ListView) view.findViewById(R.id.listview);
        ArrayList<String> likes = new ArrayList<String>();
        likes.add("1");
        currentCategory = "Help";

        Post newPost = new Post("Person1", "title1", "23 jan", "I need help", likes, "help", 1, 0);
        Post newPost2 = new Post("Person2", "title2", "24 jan", "Me too", likes, "help", 2, 0);
        Post newPost3 = new Post("Person3", "title3", "25 jan", "Me same", likes, "help",3, 0);

        postList = new ArrayList<>();

        postList.add(newPost);
        postList.add(newPost2);
        postList.add(newPost3);


        PostListAdapter adapter = new PostListAdapter(getContext(), R.layout.adapter_view_layout, postList);
        listView.setAdapter(adapter);


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

            case R.id.messageButton:
                String username = "test";
                String title = messageTitle.getText().toString();
                String timestamp = "Timestamp";
                String message = messageInput.getText().toString();
                ArrayList<String> likes = new ArrayList<String>();
                likes.add("1");
                // messageShow.setText(message);
                // messageShow.append(title + "\n" + message + "\n");

                Post newWrittenPost = new Post(username, title, timestamp, message, likes, currentCategory, id, parentId);
                System.out.println(newWrittenPost.printInformation());
                postList.add(newWrittenPost);
                break;
            case R.id.btnForumToStart:
                ((MainActivity) getActivity()).setViewPager(1);
                break;

        }
    }
}
