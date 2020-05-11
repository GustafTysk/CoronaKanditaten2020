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

    Credentials credentials;
    Retrofit retrofit;
    ClientAPI clientAPI;
    String baseurl = "http://192.168.42.145:8080/tja/webapi/";

    public Datahandler() {

        CreatDummylocations();

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

        heatmaplocations[0] = new Location("59.858565", "17.638927", "2020-10-15", 0, 0, 2, 0, 3, 0, 1, 1, 0, 2, "2020-10-17 10:30");
        heatmaplocations[1] = new Location("59.858568", "17.638927", "2020-10-15", 1, 2, 3, 0, 2, 1, 1, 2, 1, 1, "2020-10-17 10:30");
        heatmaplocations[2] = new Location("59.858561", "17.638927", "2020-10-15", 2, 2, 1, 0, 3, 2, 1, 3, 2, 2, "2020-10-17 10:30");
        heatmaplocations[3] = new Location("59.858572", "17.638927", "2020-10-15", 3, 3, 2, 0, 1, 3, 1, 1, 0, 3, "2020-10-17 10:30");
        heatmaplocations[4] = new Location("59.858522", "17.638927", "2020-10-16", 4, 0, 0, 3, 3, 2, 1, 2, 0, 2, "2020-10-17 10:30");
        heatmaplocations[5] = new Location("59.858362", "17.638927", "2020-10-15", 5, 0, 3, 0, 2, 2, 1, 3, 1, 1, "2020-10-17 10:30");
        heatmaplocations[6] = new Location("59.858462", "17.638927", "2020-10-15", 6, 2, 0, 1, 3, 1, 1, 1, 0, 0, "2020-10-17 10:30");
        heatmaplocations[7] = new Location("59.858562", "17.638927", "2020-10-16", 7, 3, 0, 2, 1, 2, 1, 2, 0, 3, "2020-10-17 10:30");
        heatmaplocations[8] = new Location("59.858762", "17.638927", "2020-10-15", 8, 0, 0, 2, 3, 1, 1, 3, 3, 2, "2020-10-17 10:30");
        heatmaplocations[9] = new Location("59.858962", "17.638927", "2020-10-15", 9, 1, 2, 0, 0, 0, 1, 1, 0, 1, "2020-10-17 10:30");
        heatmaplocations[10] = new Location("59.856362", "17.638927", "2020-10-15", 10, 2, 1, 0, 3, 3, 1, 1, 0, 2, "2020-10-17 10:30");
        heatmaplocations[11] = new Location("59.857462", "17.638927", "2020-10-15", 11, 3, 2, 0, 1, 0, 1, 1, 1, 3, "2020-10-17 10:30");


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
















































    public void save(String textSave, Context ctx) {
        FileOutputStream fos = null;
        try {
            fos = ctx.openFileOutput(FILE_NAME, ctx.MODE_PRIVATE);
            fos.write(textSave.getBytes());
            //Toast.makeText(Context , this, "Saved to " + ctx.getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {

            
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(Context ctx) {
        FileInputStream fis = null;
        try {
            fis = ctx.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String textSave;
            while((textSave = br.readLine()) != null) {
                sb.append(textSave).append("\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
