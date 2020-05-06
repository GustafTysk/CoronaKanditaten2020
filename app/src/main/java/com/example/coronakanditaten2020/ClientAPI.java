
package com.example.coronakanditaten2020;



import android.text.GetChars;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClientAPI {


    @GET("location/{regtime}")
    Call<ArrayList<Location>> getAlllocations(@Path("regtime") String regtime);

    @GET("location/{email}/user/security")
    Call<ArrayList<Location>> getuserlocations(@Header("authorization") String Auth, @Path("email") String email);

    @POST("location/{email}/user/security")
    Call<String> createuserlocations(@Header("authorization") String Auth,@Path("email") String email, @Body ArrayList<Location> userlocation);

    @PUT("location/{email}/user/security")
    Call<String> updateuserlocation(@Header("authorization") String Auth,@Path("email") String email, @Body ArrayList<Location> userlocation);

    @DELETE("location/{email}/user/security")
    Call<Boolean> removeUserlocations(@Header("authorization") String Auth,@Path("email") String email);

    @GET("User/{email}/security")
    Call<User> getuser(@Header("authorization") String Auth, @Path("email") String email);

    @POST("User")
    Call<Boolean> createuser( @Body User user);

    @PUT("User/{email}/security")
    Call<Boolean> updateuser(@Header("authorization") String Auth,@Path("email") String email, @Body User user);

    @GET("post/{number}")
    Call<ArrayList<Post>> getresentposts(@Path("number") String number);

    @POST("post/{email}/security")
    Call<Post> creatpost(@Header("authorization") @Path("email") String email);

    @POST("post/answer/{email}/security")
    Call<Post> creatanswerpost(@Header("authorization") @Path("email") String email);

    @POST("post/like/{email}/{username}/security")
    Call<Post> creatanswerpost(@Header("authorization") @Path("email") String email, @Path("username") String username);










}

