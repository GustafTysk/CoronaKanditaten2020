package com.example.coronakanditaten2020;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Path;

public class Datahandler {
    private static final String FILE_NAME = "example.txt";
    Location[] heatmaplocations = new Location[12];
    ArrayList<Location> heatlocations;
    ArrayList<Location> Userlocations;
    User user;
    ArrayList<Post> viewPosts;
    ArrayList<ArrayList> userinfo;

    Credentials credentials;
    Retrofit retrofit;
    ClientAPI clientAPI;
    String baseurl = "http://192.168.42.145:8080/tjaa/webapi/";
    ArrayList<Post> topPost;

    public Datahandler() {

        CreatDummylocations();
        CreateDummyTopPost();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        clientAPI = retrofit.create(ClientAPI.class);
    }

    public Location[] getHeatmaplocations() {
        return heatmaplocations;
    }

    public ArrayList<Location> getHeatlocations() {
        return heatlocations;
    }

    public ArrayList<Location> getUserlocations() {
        return Userlocations;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Post> getViewPosts() {
        return viewPosts;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public ClientAPI getClientAPI() {
        return clientAPI;
    }

    public String getBaseurl() {
        return baseurl;
    }

    void CreatDummylocations() {

        heatmaplocations[0] = new Location("0", "0", "0", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, "2020-10-17 10:30");
        heatmaplocations[1] = new Location("58.858568", "17.638922", "2020-5-15", 1, 2, 3, 0, 2, 1, 1, 2, 1, 1, "2020-10-17 10:30");
        heatmaplocations[2] = new Location("57.858531", "17.63937", "2020-5-11", 2, 2, 1, 0, 3, 2, 1, 3, 2, 2, "2020-10-17 10:30");
        heatmaplocations[3] = new Location("56.858572", "17.638927", "2020-5-10", 3, 3, 2, 0, 1, 3, 1, 1, 0, 3, "2020-10-17 10:30");
        heatmaplocations[4] = new Location("55.858522", "17.638947", "2020-5-13", 4, 0, 0, 3, 3, 2, 1, 2, 0, 2, "2020-10-17 10:30");
        heatmaplocations[5] = new Location("54.858382", "17.638927", "2020-5-12", 5, 0, 3, 0, 2, 2, 1, 3, 1, 1, "2020-10-17 10:30");
        heatmaplocations[6] = new Location("53.858462", "17.638927", "2020-4-28", 6, 2, 0, 1, 3, 1, 1, 1, 0, 0, "2020-10-17 10:30");
        heatmaplocations[7] = new Location("52.85862", "17.6387", "2020-4-29", 7, 3, 0, 2, 1, 2, 1, 2, 0, 3, "2020-10-17 10:30");
        heatmaplocations[8] = new Location("51.858762", "17.638927", "2020-4-30", 8, 0, 0, 2, 3, 1, 1, 3, 3, 2, "2020-10-17 10:30");
        heatmaplocations[9] = new Location("50.85962", "17.63897", "2020-5-14", 9, 1, 2, 0, 0, 0, 1, 1, 0, 1, "2020-10-17 10:30");
        heatmaplocations[10] = new Location("59.856362", "17.638927", "2020-10-15", 10, 2, 1, 0, 3, 3, 1, 1, 0, 2, "2020-10-17 10:30");
        heatmaplocations[11] = new Location("59.857462", "17.638927", "2020-10-16", 11, 3, 2, 0, 1, 0, 1, 1, 1, 3, "2020-10-17 10:30");

    }

    void CreateDummyTopPost(){

        viewPosts = new ArrayList<>();

        Post newPost = new Post("Person1", "adasjkd@dsjkf.com", "title1", "23 jan", "I need help", 0, "help", 1, 0);
        Post newPost2 = new Post("Person2", "sfdjkfjdsfl@fdk.com", "title2", "24 jan", "Me too", 2, "help", 2, 0);
        Post newPost3 = new Post("Person3", "jdassl@dhd.se", "title3", "25 jan", "Me same", 3, "help",3, 0);
        Post newPost4 = new Post("Person4", "sjdfds@fjdl.com", "title4", "26 jan", "I need help", 0, "rec", 4, 0);
        Post newPost5 = new Post("Person5", "hddjd@dhd.com","title5", "27 jan", "Me too", 1, "rec", 5, 0);
        Post newPost6 = new Post("Person6", "dsdlks@dkjd.se","title6", "28 jan", "Me same", 0, "rec",6, 0);
        Post newPost7 = new Post("Person7", "jds@jd.se","title7", "29 jan", "I need help", 4, "rec", 7, 0);
        Post newPost8 = new Post("Person8", "skdjdk@dkdl.com","title8", "30 jan", "Me too", 2, "rec", 8, 0);
        Post newPost9 = new Post("Person9", "dskld@sklsd.se","title9", "31 jan", "Me same",5, "help",9, 0);

        viewPosts.add(newPost);
        viewPosts.add(newPost2);
        viewPosts.add(newPost3);
        viewPosts.add(newPost4);
        viewPosts.add(newPost5);
        viewPosts.add(newPost6);
        viewPosts.add(newPost7);
        viewPosts.add(newPost8);
        viewPosts.add(newPost9);

    }


    public void getalllserverocations(String regtime) {
        Call<ArrayList<Location>> getAlllocations = clientAPI.getAlllocations(regtime);
        getAlllocations.enqueue(new Callback<ArrayList<Location>>() {
            @Override
            public void onResponse(Call<ArrayList<Location>> call, Response<ArrayList<Location>> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.message());

                }
                heatlocations = response.body();
            }

            @Override
            public void onFailure(Call<ArrayList<Location>> call, Throwable t) {

                System.out.println("errror");
            }
        });
    }

    public void getuserserverlocations() {
        Call<ArrayList<Location>> getuserlocations = clientAPI.getuserlocations(credentials.encrypt, credentials.Email);
        getuserlocations.enqueue(new Callback<ArrayList<Location>>() {
            @Override
            public void onResponse(Call<ArrayList<Location>> call, Response<ArrayList<Location>> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.code());
                }
                Userlocations = response.body();
                System.out.println("userlocations have been added");
            }

            @Override
            public void onFailure(Call<ArrayList<Location>> call, Throwable t) {

                System.out.println(t);
            }
        });
    }

    public void getserveruser(){
        Call<User> getUser=clientAPI.getuser(credentials.encrypt,credentials.Email);
        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    System.out.println("there has been an error");
                }
                else{
                    System.out.println(response.toString());
                    user=response.body();
                    System.out.println(user.getEmail());
                    System.out.println("user has succefully collected");

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("failed to connect to server");

            }
        });
    }

public void getserveruserinfo(String timestamp){
        Call<ArrayList<ArrayList>> getuserinfo=clientAPI.GetUserinfo(timestamp);
        getuserinfo.enqueue(new Callback<ArrayList<ArrayList>>() {
            @Override
            public void onResponse(Call<ArrayList<ArrayList>> call, Response<ArrayList<ArrayList>> response) {
                if(!response.isSuccessful()){
                    System.out.println("there has been an error");
                }
                else{
                    System.out.println(response.toString());
                    userinfo=response.body();

                    System.out.println("userinfos has succefully collected");

                }
            }

            @Override
            public void onFailure(Call<ArrayList<ArrayList>> call, Throwable t) {
                System.out.println("failed to connect to server");

            }
        });
}

    public void getTopPost(int number){
        Call<ArrayList<Post>> getTopPost=clientAPI.getresentposts(number);
        getTopPost.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                if(!response.isSuccessful()){
                    System.out.println("there has been an error");
                }
                else{
                    System.out.println(response.toString());
                    viewPosts=response.body();

                    System.out.println("userinfos has succefully collected");

                }

            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                System.out.println("failed to connect to server");
            }
        });
    }

}
