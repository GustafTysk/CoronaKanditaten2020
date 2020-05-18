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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForumFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button messageButton;
    private Button btnMostLiked;
    private Button btnFilterRec;
    private Button btnFilterAll;
    private Button btnSearch;

    private EditText searchInput;
    private EditText messageInput;
    private EditText messageTitle;
    private TextView usernameShow;
    ArrayList<Post> topPost;
    private String currentCategory;
    private String username;

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
        btnFilterRec = (Button) view.findViewById(R.id.btnUserPosts);
        btnFilterRec.setOnClickListener(this);
        btnFilterAll = (Button) view.findViewById(R.id.btnFilterAll);
        btnFilterAll.setOnClickListener(this);
        btnSearch = (Button) view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        username = ((MainActivity)getActivity()).datahandler.user.getUsername();

        usernameShow = (TextView) view.findViewById(R.id.usernameShow);
        usernameShow.setText(username);

        messageButton = (Button) view.findViewById(R.id.messageButton);
        messageButton.setOnClickListener(this);
//        btnForumToStart = (Button) view.findViewById(R.id.btnForumToStart);
//        btnForumToStart.setOnClickListener(this);

        messageInput = (EditText) view.findViewById(R.id.messageInput);
        messageTitle = (EditText) view.findViewById(R.id.messageTitle);

        searchInput = (EditText) view.findViewById(R.id.searchInput);
        listView = (ListView) view.findViewById(R.id.listview);

        currentCategory = "Help";

        postList = new ArrayList<>(topPost);
        copyList = new ArrayList<>(topPost);

        Collections.reverse(postList);

        adapter = new PostListAdapter(getContext(), R.layout.adapter_view_layout, postList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        removePosts();

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

        if(thePostParentId == 0) {
            Post thePost = postList.get(thePostId);
            System.out.println(thePost.printInformation());
            postList.clear();
            postList.add(thePost);
            getComments(thePost);
        }
        // getserverComments(thePost);
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

    public void getComments(Post post) {
        //run this to get get commants att commantslist
        // getserverComments(post);

        ArrayList<Post> tempPost= new ArrayList<>();
        tempPost.add(new Post("Comment1", "abc@sdfjk.com", "Comment1", "1 jan", "I agrre", 2, "comment", 1));
        tempPost.add(new Post("Comment2", "dsadlsa@fskjd.com", "Comment2", "2 jan", "Interesting", 3, "comment", 2));
        tempPost.add(new Post("Comment3", "adssl@fsdk.com", "Comment3", "3 jan", "Okay", 0, "comment", 3));
        tempPost.add(new Post("Comment4", "fdsj@fmf.se", "Comment4", "4 jan", "I do not agree", 2, "comment", 2));
        tempPost.add(new Post("Comment5", "sdksa@sdjk.se", "Comment5", "5 jan", "Okay okay", 5, "comment", 4));
        tempPost.add(new Post("Comment6", "cdds@adksj.com", "Comment6", "6 jan", "I see", 6, "comment", 1));
        tempPost.add(new Post("Comment7", "comdslsk@nskjd.se", "Comment7", "7 jan", "Okay", 0, "comment", 7));

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
                String email = ((MainActivity)getActivity()).datahandler.user.getEmail();
                String title = messageTitle.getText().toString();
                String timestamp = "3 jan";
                String message = messageInput.getText().toString();
                if(thePostId != 0) {
                    Post newWrittenPost = new Post(username,email ,title, timestamp, message, 0, "comment", 30, thePostParentId);
                    postList.add(newWrittenPost);
                    copyList.add(newWrittenPost);
                    //sendanswertoserver(newWrittenPost);


                }
                else{
                    Post newWrittenPost = new Post(username, email,title, timestamp, message, 0, "top", 30, 0);
                    System.out.println(newWrittenPost.printInformation());
                    postList.clear();
                    for(Post post : copyList){
                        postList.add(post);
                    }
                    postList.add(newWrittenPost);
                    Collections.reverse(postList);
                    copyList.add(newWrittenPost);
                    // sendposttoserver(newWrittenPost);


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
                for(Post post : copyList){
                    postList.add(post);
                }
                Collections.sort(postList, Post.DESCENDING_COMPARATOR);
                adapter.notifyDataSetChanged();
                //run the statment under whhen you are connected to database
                //postList=getmostliked()
                //getmostliked();

                break;

            case R.id.btnUserPosts:
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
                    if(post.parentId == 0){
                        postList.add(post);
                    }
                }
                removePosts();

                Collections.reverse(postList);

                adapter.notifyDataSetChanged();
                break;

            case R.id.btnSearch:
                String inputsearch = searchInput.getText().toString();
                //input search är det som användaren vill söka på.
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
                    Toast.makeText(getContext(), getString(R.string.fail_to_get_post), Toast.LENGTH_LONG).show();
                }
                else {
                    ((MainActivity) getActivity()).datahandler.viewPosts = response.body();
                    postList = ((MainActivity) getActivity()).datahandler.viewPosts;
                    Collections.sort(postList, Post.DESCENDING_COMPARATOR);

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
                    commentList = response.body();

//                    for (Post testPost : tempPost) {
//                        if(testPost.getParentId()==post.getId()){
//                            System.out.println("Hej");
//                            postList.add(testPost);
//                        }
//                    }
                    postList = commentList;
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
                    postList = ((MainActivity) getActivity()).datahandler.viewPosts;
                    copyList = new ArrayList<>(topPost);
                    postList.clear();

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

    public void GetOwnLikedPosts(){
        Call<ArrayList<Post>> GetOwnLikedPosts=((MainActivity)getActivity()).datahandler.clientAPI.GetOwnLikedPosts(
                ((MainActivity)getActivity()).datahandler.credentials.encrypt,
                ((MainActivity)getActivity()).datahandler.credentials.Email);
        GetOwnLikedPosts.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                if(!response.isSuccessful()) {
                    System.out.println(response);
                    Toast.makeText(getContext(), getString(R.string.fail_to_get_post), Toast.LENGTH_LONG).show();
                }
                else {
                ((MainActivity)getActivity()).datahandler.viewPosts=response.body();
                postList=((MainActivity)getActivity()).datahandler.viewPosts;

                System.out.println("got mosts liked post");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.fail_connect_to_server), Toast.LENGTH_LONG).show();

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
                    Toast.makeText(getContext(), getString(R.string.fail_to_get_post), Toast.LENGTH_LONG).show();
                }
                else{
                    ((MainActivity)getActivity()).datahandler.viewPosts.add(post);
                    postList=((MainActivity)getActivity()).datahandler.viewPosts;
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.fail_connect_to_server), Toast.LENGTH_LONG).show();

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
                    Toast.makeText(getContext(), getString(R.string.fail_to_add_post), Toast.LENGTH_LONG).show();
                }
                else {
                    commentList.add(post);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.fail_connect_to_server), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getContext(), getString(R.string.fail_to_add_like), Toast.LENGTH_LONG).show();
                }else {
                    //to be added when micihiel decides howyou like a post

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.fail_connect_to_server), Toast.LENGTH_LONG).show();

            }
        });

    }










}
