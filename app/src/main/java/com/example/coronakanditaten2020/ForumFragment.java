package com.example.coronakanditaten2020;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class ForumFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button messageButton;
    private Button btnForumToStart;

    private Button btnFilterHelp;
    private Button btnFilterRec;
    private Button btnFilterAll;

    private EditText searchFilter;
    private EditText messageInput;
    private EditText messageTitle;
    private TextView usernameShow;
    ArrayList<Post> topPost;
    private String currentCategory;

    public ListView listView;

    public int id = 1;
    public int parentId = 0;
    int thePostId;
    int thePostParentId;

    public List<Post>copyList;
    public List<Post>commentList;
    public ArrayList<Post>postList;
    private PostListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum, container, false);

        topPost =((MainActivity)getActivity()).datahandler.viewPosts;

        btnFilterHelp = (Button) view.findViewById(R.id.btnFilterHelp);
        btnFilterHelp.setOnClickListener(this);
        btnFilterRec = (Button) view.findViewById(R.id.btnFilterRec);
        btnFilterRec.setOnClickListener(this);
        btnFilterAll = (Button) view.findViewById(R.id.btnFilterAll);
        btnFilterAll.setOnClickListener(this);

        messageButton = (Button) view.findViewById(R.id.messageButton);
        messageButton.setOnClickListener(this);
        btnForumToStart = (Button) view.findViewById(R.id.btnForumToStart);
        btnForumToStart.setOnClickListener(this);
        messageInput = (EditText) view.findViewById(R.id.messageInput);
        messageTitle = (EditText) view.findViewById(R.id.messageTitle);

        searchFilter = (EditText) view.findViewById(R.id.searchFilter);
        listView = (ListView) view.findViewById(R.id.listview);
        currentCategory = "Help";

        postList = new ArrayList<>(topPost);
        copyList = new ArrayList<>(topPost);

        adapter = new PostListAdapter(getContext(), R.layout.adapter_view_layout, postList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        searchFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (ForumFragment.this).adapter.getFilter().filter(s.toString());

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);

        long postId = id;
        thePostId = (int) postId;
        String theCategory = postList.get(thePostId).getCategory();
        thePostParentId = postList.get(thePostId).getParentId();
        System.out.println(thePostId);
        System.out.println(thePostParentId);

        Post thePost = postList.get(thePostId);
        System.out.println(thePost.printInformation());
        postList.clear();
        postList.add(thePost);
        commentList = getComments(thePost);
        adapter.notifyDataSetChanged();

    }

    public void removeComments() {
        postList.clear();
        for (Post post: copyList) {
            if(post.getParentId() == 0) {
                postList.add(post);
            }
        }
    }

    public ArrayList<Post> getComments(Post post) {
        ArrayList<Post> tempPost= new ArrayList<Post>();
        tempPost.add(new Post("Comment1", "comment1", "23 jan", "Hello1", 0, "help", 1, 0));
        tempPost.add(new Post("Comment2", "comment2", "23 jan", "Hello2", 0, "help", 1, 2));
        tempPost.add(new Post("Comment3", "comment3", "23 jan", "Hello3", 0, "help", 1, 3));
        tempPost.add(new Post("Comment4", "comment4", "23 jan", "Hello4", 0, "help", 1, 2));
        tempPost.add(new Post("Comment5", "comment5", "23 jan", "Hello5", 0, "help", 1, 4));
        tempPost.add(new Post("Comment6", "comment6", "23 jan", "Hello6", 0, "help", 1, 1));
        tempPost.add(new Post("Comment7", "comment7", "23 jan", "Hello7", 0, "help", 1, 0));
    return tempPost;
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
                if(thePostId != 0) {
                    Post newWrittenPost = new Post(username, title, timestamp, message, 0, "help", 30, thePostParentId);
                    postList.add(newWrittenPost);
                }
                else{
                    Post newWrittenPost = new Post(username, title, timestamp, message, 0, "help", 30, 0);
                    System.out.println(newWrittenPost.printInformation());
                    postList.add(newWrittenPost);
                }
                adapter.notifyDataSetChanged();

                messageTitle.setText("");
                messageInput.setText("");
                break;
            case R.id.btnForumToStart:
                ((MainActivity) getActivity()).setViewPager(1);
                break;

            case R.id.btnFilterHelp:
                postList.clear();
                for (Post post: copyList) {
                    postList.add(post);
                }

                List<Post> noShow = new ArrayList<Post>();
                for (Post post: postList) {
                    if(post.getCategory().equals("no")){
                        noShow.add(post);
                    }
                }
                postList.removeAll(noShow);
                adapter.notifyDataSetChanged();
                break;

            case R.id.btnFilterRec:
                postList.clear();
                for (Post post: copyList) {
                    postList.add(post);
                }

                List<Post> noShow2 = new ArrayList<Post>();
                for (Post post: postList) {

                    if(post.getCategory().equals("help")){
                        noShow2.add(post);
                    }
                }
                postList.removeAll(noShow2);
                adapter.notifyDataSetChanged();
                break;

            case R.id.btnFilterAll:
                postList.clear();
                for (Post post: copyList) {
                    postList.add(post);
                }

                adapter.notifyDataSetChanged();
                break;
        }
    }
}
