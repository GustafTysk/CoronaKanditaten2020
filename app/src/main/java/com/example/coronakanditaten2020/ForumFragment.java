package com.example.coronakanditaten2020;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

    private EditText searchFilter;

    private EditText messageInput;
    private EditText messageTitle;

    private String currentCategory;

    public ListView listView;

    public int id = 1;
    public int parentId = 0;

    public ArrayList<Post>postList;
    private PostListAdapter adapter;

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

        searchFilter = (EditText) view.findViewById(R.id.searchFilter);
        listView = (ListView) view.findViewById(R.id.listview);
        ArrayList<String> likes = new ArrayList<String>();

        likes.add("1");
        currentCategory = "Help";

        Post newPost = new Post("Person1", "title1", "23 jan", "I need help", likes, "help", 1, 0);
        Post newPost2 = new Post("Person2", "title2", "24 jan", "Me too", likes, "help", 2, 0);
        Post newPost3 = new Post("Person3", "title3", "25 jan", "Me same", likes, "help",3, 0);
        Post newPost4 = new Post("Person4", "title4", "26 jan", "I need help", likes, "help", 4, 0);
        Post newPost5 = new Post("Person5", "title5", "27 jan", "Me too", likes, "help", 5, 0);
        Post newPost6 = new Post("Person6", "title6", "28 jan", "Me same", likes, "help",6, 0);
        Post newPost7 = new Post("Person7", "title7", "29 jan", "I need help", likes, "help", 7, 0);
        Post newPost8 = new Post("Person8", "title8", "30 jan", "Me too", likes, "help", 8, 0);
        Post newPost9 = new Post("Person9", "title9", "31 jan", "Me same", likes, "help",9, 0);

        postList = new ArrayList<>();

        postList.add(newPost);
        postList.add(newPost2);
        postList.add(newPost3);
        postList.add(newPost4);
        postList.add(newPost5);
        postList.add(newPost6);
        postList.add(newPost7);
        postList.add(newPost8);
        postList.add(newPost9);


        adapter = new PostListAdapter(getContext(), R.layout.adapter_view_layout, postList);
        listView.setAdapter(adapter);

        searchFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (ForumFragment.this).adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
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

                Post newWrittenPost = new Post(username, title, timestamp, message, likes, "help", 30, 0);
                System.out.println(newWrittenPost.printInformation());
                postList.add(newWrittenPost);
                adapter.notifyDataSetChanged();
                break;
            case R.id.btnForumToStart:
                ((MainActivity) getActivity()).setViewPager(0);
                break;

        }
    }
}
