package com.example.coronakanditaten2020;

import android.app.AlertDialog;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.util.Date;


public class ForumFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button messageButton;
    private Button btnMostLiked;
    private Button btnRecent;
    private Button btnMyPost;
    private ImageButton btnSearch;

    private ImageView forumInfo;

    private EditText searchInput;
    private EditText messageInput;
    private EditText messageTitle;
    private TextView usernameShow;
    ArrayList<Post> topPost;
    private String username;

    public ListView listView;

    public int id = 1;
    public int parentId = 0;
    int thePostId;
    int thePostParentId;

    public ArrayList<Post>copyList;
    public ArrayList<Post>commentList;
    public ArrayList<Post>postList;
    public View view;


    private PostListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forum, container, false);

        topPost =((MainActivity)getActivity()).datahandler.viewPosts;
        System.out.println("post" + topPost);

        btnMostLiked = (Button) view.findViewById(R.id.btnMostLiked);
        btnMostLiked.setOnClickListener(this);
        btnRecent = (Button) view.findViewById(R.id.btnRecent);
        btnRecent.setOnClickListener(this);
        btnMyPost = (Button) view.findViewById(R.id.btnMyPost);
        btnMyPost.setOnClickListener(this);
        btnSearch = (ImageButton) view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        usernameShow = (TextView) view.findViewById(R.id.usernameShow);
        //usernameShow.setText(username);

        messageButton = (Button) view.findViewById(R.id.messageButton);
        messageButton.setOnClickListener(this);
//        btnForumToStart = (Button) view.findViewById(R.id.btnForumToStart);
//        btnForumToStart.setOnClickListener(this);

        messageInput = (EditText) view.findViewById(R.id.messageInput);
        messageTitle = (EditText) view.findViewById(R.id.messageTitle);

        searchInput = (EditText) view.findViewById(R.id.searchInput);
        listView = (ListView) view.findViewById(R.id.listview);

        postList = topPost;
        copyList = topPost;

        //Collections.reverse(postList);

        adapter = new PostListAdapter(getContext(), R.layout.adapter_view_layout, postList, ((MainActivity)getActivity()).datahandler);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        forumInfo = (ImageView) view.findViewById(R.id.forumInfo);
        forumInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                alert.setTitle(getString(R.string.info_title_forum));
                alert.setMessage(getString(R.string.info_message_forum));
                alert.setNegativeButton(android.R.string.ok, null);
                alert.show();
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
        int postIdInt = (int) postId;
        thePostId = postList.get(postIdInt).getId();
        System.out.println(thePostId);
        if(adapter.selectedItem == position) {
            adapter.selectedItem = -1;
        }
        else{
            adapter.selectedItem = position;
        }
        Post thePost;
        for (Post post : postList) {
            if(post.getId()==thePostId){
                thePost = post;
                getserverComments(thePost);

            }
        }

        adapter.notifyDataSetChanged();
    }

    public void removePosts() {
        for (Post post: postList) {
            if(post.getCategory() == "remove") {
                postList.remove(post);
                copyList.remove(post);
            }
        }
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.messageButton:
                ((MainActivity)getActivity()).datahandler.getserveruser();
                username = ((MainActivity)getActivity()).datahandler.user.getUsername();
                String email = ((MainActivity)getActivity()).datahandler.user.getEmail();
                String title = messageTitle.getText().toString();
                Date date = new Date();
                String timestamp=date.toString();
                String message = messageInput.getText().toString();
                System.out.println(thePostParentId);
                if(adapter.selectedItem != -1) {
                    Post newWrittenPost = new Post(username,email ,title, timestamp, message, 0, "comment",0 , thePostId);
                    sendanswertoserver(newWrittenPost);
                }

                else{
                    Post newWrittenPost = new Post(username, email,title, timestamp, message, 0, "top", 0, 0);
                    System.out.println(newWrittenPost.printInformation());

                    sendposttoserver(newWrittenPost);


                }
                adapter.notifyDataSetChanged();

                messageTitle.setText("");
                messageInput.setText("");
                break;

            case R.id.btnMostLiked:

                getmostliked();


                break;

            case R.id.btnRecent:
                gettop(1);


                break;

            case R.id.btnMyPost:
                getuserpost();
                break;

            case R.id.btnSearch:
                String inputsearch = searchInput.getText().toString();
                Search(inputsearch);
                break;
        }
    }


    public void getmostliked(){
        Call<ArrayList<Post>> getmostliked=((MainActivity)getActivity()).datahandler.clientAPI.getMostlikedpost("475474");
        getmostliked.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                if(!response.isSuccessful()) {
                    System.out.println(response);
                    Toast.makeText(getContext(), getString(R.string.fail_to_get_post), Toast.LENGTH_LONG).show();
                }
                else {
                    postList.clear();
                    ((MainActivity) getActivity()).datahandler.viewPosts = response.body();
                    copyList = ((MainActivity) getActivity()).datahandler.viewPosts;
                    for (Post post: copyList){
                        postList.add(post);
                    }
                    System.out.println(postList.size());

                    adapter.notifyDataSetChanged();
                    System.out.println("got mosts liked post");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.fail_connect_to_server), Toast.LENGTH_LONG).show();

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
                    Toast.makeText(getContext(), getString(R.string.fail_to_get_post), Toast.LENGTH_LONG).show();
                }
                else {
                    postList.clear();
                    if (commentList != null){
                        commentList.clear();
                    }
                    commentList = response.body();
                   // ((MainActivity)getActivity()).datahandler.viewPosts=commentList;
                    postList.add(post);
                    for (Post post: commentList){
                        postList.add(post);
                    }
                    adapter.notifyDataSetChanged();
                    System.out.println("got comment posts");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.fail_connect_to_server), Toast.LENGTH_LONG).show();

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
                    Toast.makeText(getContext(), getString(R.string.fail_to_get_post), Toast.LENGTH_LONG).show();
                }
                else {

                    ((MainActivity) getActivity()).datahandler.viewPosts = response.body();
                    postList.clear();
                    copyList = ((MainActivity) getActivity()).datahandler.viewPosts;
                    for (Post post: copyList){
                        postList.add(post);
                    }

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.fail_connect_to_server), Toast.LENGTH_LONG).show();

            }
        });
    }



    public void sendposttoserver(Post post){
        Call<Post> sendposttoserver=((MainActivity)getActivity()).datahandler.clientAPI.creatpost(
                ((MainActivity)getActivity()).datahandler.credentials.encrypt,
                ((MainActivity)getActivity()).datahandler.credentials.Email,post);
        sendposttoserver.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), getString(R.string.fail_to_get_post), Toast.LENGTH_LONG).show();
                }
                else{
                    postList.clear();
                    copyList= ((MainActivity)getActivity()).datahandler.viewPosts;
                    for (Post post: copyList){
                        postList.add(post);
                    }
                    ((MainActivity)getActivity()).datahandler.viewPosts.add(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.fail_connect_to_server), Toast.LENGTH_LONG).show();

            }
        });
    }

    public void sendanswertoserver(Post post){
        Call<Post> sendanswertoserver=((MainActivity)getActivity()).datahandler.clientAPI.creatanswerpost(
                ((MainActivity)getActivity()).datahandler.credentials.encrypt,
                ((MainActivity)getActivity()).datahandler.credentials.Email,post);
        sendanswertoserver.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), getString(R.string.fail_to_add_post), Toast.LENGTH_LONG).show();
                }
                else {
                    commentList.add(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.fail_connect_to_server), Toast.LENGTH_LONG).show();
            }
        });
    }





    public void  Search(String search){
        System.out.println(search);
        Call<ArrayList<Post>> GetPostBySearch=((MainActivity)getActivity()).datahandler.clientAPI.GetPostBySearch(search);
        GetPostBySearch.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "failed to seearch", Toast.LENGTH_LONG).show();
                    System.out.println(response);
                }

                else{
                    ArrayList<Post> serachres=response.body();
                    if(serachres.size()==0){
                        Toast.makeText(getContext(), "did not find posts", Toast.LENGTH_LONG).show();
                        return;
                    }
                    ((MainActivity)getActivity()).datahandler.viewPosts=response.body();
                    postList.clear();
                    copyList= ((MainActivity)getActivity()).datahandler.viewPosts;
                    for (Post post: copyList){
                        postList.add(post);
                    }
                    adapter.notifyDataSetChanged();



                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.fail_connect_to_server), Toast.LENGTH_LONG).show();
            }
        });
    }


        public void gettop(int num) {
        Call<ArrayList<Post>> getTopPost=((MainActivity)getActivity()).datahandler.clientAPI.getresentposts(num);
        getTopPost.enqueue(new Callback<ArrayList<Post>>() {
        @Override
        public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
            if(!response.isSuccessful()){
                System.out.println("there has been an error");
            }
            else{
                ((MainActivity)getActivity()).datahandler.viewPosts=response.body();
                postList.clear();
                copyList= ((MainActivity)getActivity()).datahandler.viewPosts;
                for (Post post: copyList){
                    postList.add(post);
                }
                adapter.selectedItem = -1;
                adapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
            System.out.println("failed to connect to server");
        }
    });


}

    public void runwhenload(){
        topPost =((MainActivity)getActivity()).datahandler.viewPosts;
        postList = topPost;
        copyList = topPost;

        //Collections.reverse(postList);
        username = ((MainActivity)getActivity()).datahandler.user.getUsername();
        usernameShow.setText(username);
        adapter = new PostListAdapter(getContext(), R.layout.adapter_view_layout, postList, ((MainActivity)getActivity()).datahandler);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        forumInfo = (ImageView) view.findViewById(R.id.forumInfo);
        forumInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                alert.setTitle(getString(R.string.info_title_forum));
                alert.setMessage(getString(R.string.info_message_forum));
                alert.setNegativeButton(android.R.string.ok, null);
                alert.show();
            }
        });

        removePosts();
    }





}
