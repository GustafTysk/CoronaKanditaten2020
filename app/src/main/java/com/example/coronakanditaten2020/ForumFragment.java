package com.example.coronakanditaten2020;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public class ForumFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button messageButton;
    private Button btnForumToStart;

    private Button btnMostLiked;
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

    public ArrayList<Post>copyList;
    public ArrayList<Post>commentList;
    public ArrayList<Post>postList;
    private PostListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum, container, false);

        topPost =((MainActivity)getActivity()).datahandler.viewPosts;

        btnMostLiked = (Button) view.findViewById(R.id.btnMostLiked);
        btnMostLiked.setOnClickListener(this);
        btnFilterRec = (Button) view.findViewById(R.id.btnFilterRec);
        btnFilterRec.setOnClickListener(this);
        btnFilterAll = (Button) view.findViewById(R.id.btnFilterAll);
        btnFilterAll.setOnClickListener(this);

        messageButton = (Button) view.findViewById(R.id.messageButton);
        messageButton.setOnClickListener(this);
//        btnForumToStart = (Button) view.findViewById(R.id.btnForumToStart);
//        btnForumToStart.setOnClickListener(this);
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

    public void setForumBottomNav(){
        ((MainActivity) requireActivity()).bottomNav = (BottomNavigationView) getView().findViewById(R.id.bottom_navigation);
        ((MainActivity) requireActivity()).bottomNav.setOnNavigationItemSelectedListener(((MainActivity) getActivity()).navListener);
        ((MainActivity) requireActivity()).bottomNav.getMenu().findItem(R.id.nav_forum).setChecked(true);
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);

        long postId = id;
        thePostId = (int) postId;
        String theCategory = postList.get(thePostId).getCategory();
        thePostParentId = postList.get(thePostId).getParentId();
        //System.out.println(thePostId);

        Post thePost = postList.get(thePostId);
        System.out.println(thePost.printInformation());
        postList.clear();
        postList.add(thePost);
        getComments(thePost);
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

    public void getComments(Post post) {
        //run this to get get commants att commantslist
        // getserverComments(post);

        ArrayList<Post> tempPost= new ArrayList<>();
        tempPost.add(new Post("Comment1", "comment1", "23 jan", "Hello1", 0, "comment", 10, 1));
        tempPost.add(new Post("Comment2", "comment2", "23 jan", "Hello2", 0, "comment", 11, 2));
        tempPost.add(new Post("Comment3", "comment3", "23 jan", "Hello3", 0, "comment", 12, 3));
        tempPost.add(new Post("Comment4", "comment4", "23 jan", "Hello4", 0, "comment", 13, 2));
        tempPost.add(new Post("Comment5", "comment5", "23 jan", "Hello5", 0, "comment", 14, 4));
        tempPost.add(new Post("Comment6", "comment6", "23 jan", "Hello6", 0, "comment", 15, 1));
        tempPost.add(new Post("Comment7", "comment7", "23 jan", "Hello7", 0, "comment", 16, 7));

        for (Post testPost : tempPost) {
            if(testPost.getParentId()==post.getId()){
                System.out.println("Hej");
                postList.add(testPost);
            }
        }
        tempPost.clear();
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
//            case R.id.btnForumToStart:
//                ((MainActivity) getActivity()).setViewPager(1);
//                break;

            case R.id.btnMostLiked:
                postList.clear();
                //run the statment under whhen you are connected to database
                //postList=getmostliked()
                for (Post post : copyList){
                    postList.add(post);
                }
                Collections.sort(postList, Post.DESCENDING_COMPARATOR);

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


    public void getmostliked(){
        Call<ArrayList<Post>> getmostliked=((MainActivity)getActivity()).datahandler.clientAPI.getMostlikedpost("");
        getmostliked.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                if(!response.isSuccessful()) {
                    System.out.println(response);
                    Toast.makeText(getContext(), "failed to get post", Toast.LENGTH_LONG).show();
                }
                else {
                    ((MainActivity) getActivity()).datahandler.viewPosts = response.body();
                    postList = ((MainActivity) getActivity()).datahandler.viewPosts;
                    adapter.notifyDataSetChanged();
                    System.out.println("got mosts liked post");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Toast.makeText(getContext(), "failed to conenct to server", Toast.LENGTH_LONG).show();

            }
        });
        }

    public void getserverComments(Post post){
        Call<ArrayList<Post>> getComments=((MainActivity)getActivity()).datahandler.clientAPI.getAllchildPosts(post.id);
        getComments.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                if(!response.isSuccessful()) {
                    System.out.println(response);
                    Toast.makeText(getContext(), "failed to get post", Toast.LENGTH_LONG).show();
                }
                else {
                    commentList = response.body();
                    postList = commentList;
                    adapter.notifyDataSetChanged();
                    System.out.println("got mosts liked post");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Toast.makeText(getContext(), "failed to conenct to server", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void getuserpost(){
        Call<ArrayList<Post>> getuserpost=((MainActivity)getActivity()).datahandler.clientAPI.GetOwnPosts(
                ((MainActivity)getActivity()).datahandler.credentials.encrypt,
                ((MainActivity)getActivity()).datahandler.credentials.Email);
        getuserpost.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                if(!response.isSuccessful()) {
                    System.out.println(response);
                    Toast.makeText(getContext(), "failed to get post", Toast.LENGTH_LONG).show();
                }
                else {
                    ((MainActivity) getActivity()).datahandler.viewPosts = response.body();
                    postList = ((MainActivity) getActivity()).datahandler.viewPosts;
                    adapter.notifyDataSetChanged();
                    System.out.println("got mosts liked post");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Toast.makeText(getContext(), "failed to conenct to server", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void GetOwnLikedPosts(){
        Call<ArrayList<Post>> GetOwnLikedPosts=((MainActivity)getActivity()).datahandler.clientAPI.GetOwnLikedPosts(
                ((MainActivity)getActivity()).datahandler.credentials.encrypt,
                ((MainActivity)getActivity()).datahandler.credentials.Email);
        GetOwnLikedPosts.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                if(!response.isSuccessful()) {
                    System.out.println(response);
                    Toast.makeText(getContext(), "failed to get post", Toast.LENGTH_LONG).show();
                }
                else {
                ((MainActivity)getActivity()).datahandler.viewPosts=response.body();
                postList=((MainActivity)getActivity()).datahandler.viewPosts;
                adapter.notifyDataSetChanged();
                System.out.println("got mosts liked post");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Toast.makeText(getContext(), "failed to conenct to server", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void sendposttoserver(Post post){
        Call<Boolean> sendposttoserver=((MainActivity)getActivity()).datahandler.clientAPI.creatpost(
                ((MainActivity)getActivity()).datahandler.credentials.encrypt,
                ((MainActivity)getActivity()).datahandler.credentials.Email,post);
        sendposttoserver.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "failed to add post to server", Toast.LENGTH_LONG).show();
                }
                else{
                    ((MainActivity)getActivity()).datahandler.viewPosts.add(post);
                    postList=((MainActivity)getActivity()).datahandler.viewPosts;
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getContext(), "failed to conenct to server", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void sendanswertoserver(Post post){
        Call<Boolean> sendanswertoserver=((MainActivity)getActivity()).datahandler.clientAPI.creatanswerpost(
                ((MainActivity)getActivity()).datahandler.credentials.encrypt,
                ((MainActivity)getActivity()).datahandler.credentials.Email,post);
        sendanswertoserver.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "failed to add post to server", Toast.LENGTH_LONG).show();
                }
                else {
                    commentList.add(post);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getContext(), "failed to conenct to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void likepost(Post post){
        Call<Boolean> likepost=((MainActivity)getActivity()).datahandler.clientAPI.likePost(
                ((MainActivity)getActivity()).datahandler.credentials.encrypt,
                ((MainActivity)getActivity()).datahandler.credentials.Email,post.id);
        likepost.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "failed to add like to server", Toast.LENGTH_LONG).show();
                }else {
                    //to be added when micihiel decides howyou like a post

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getContext(), "failed to conenct to server", Toast.LENGTH_LONG).show();

            }
        });

    }










}
