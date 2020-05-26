
package com.example.coronakanditaten2020;



import android.text.GetChars;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.http.HttpHeaders;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClientAPI {

    //location resources
    @GET("location/{regtime}")
    Call<ArrayList<Location>> getAlllocations(@Path("regtime") String regtime);

    @GET("location/{email}/user/security")
    Call<ArrayList<Location>> getuserlocations(@Header("authorization") String Auth, @Path("email") String email);

    @POST("location/{email}/user/security")
    Call<Boolean> createuserlocations(@Header("authorization") String Auth, @Path("email") String email, @Body ArrayList<Location> userlocation);

    @PUT("location/{email}/user/security")
    Call<String> updateuserlocation(@Header("authorization") String Auth,@Path("email") String email, @Body ArrayList<Location> userlocation);

    @DELETE("location/{email}/user/security")
    Call<Boolean> removeUserlocations(@Header("authorization") String Auth,@Path("email") String email);

    //User resources

    @GET("User/login/{email}/{password}/security")
    Call<Boolean> login(@Header("authorization") String Auth,@Path("email") String email,@Path("password") String password);

    @GET("User/{email}/security")
    Call<User> getuser(@Header("authorization") String Auth, @Path("email") String email);

    @GET ("User/info/{timestamp}")
    Call <ArrayList<ArrayList>> GetUserinfo(@Path("timestamp") String timestamp);

    @GET ("User/num/{timestamp}")
    Call<Integer>  getNumberOfUsers(@Path("timestamp") String timestamp);




    @POST("User/{ver}")
    Call<Boolean> createuser( @Path("ver") String ver ,@Body User user);

    @PUT("User/{email}/security")
    Call<Boolean> updateuser(@Header("authorization") String Auth,@Path("email") String email, @Body User user);

    @DELETE("User/{email}/security")
    Call<Boolean> Deleteuser(@Header("authorization") String Auth,@Path("email") String email);

    @PUT("newUsername/{email}/{newUsername}/security")
    Call<Boolean> changeUserName(@Header("authorization") String Auth,@Path("email") String email,@Path("newUsername") String newUsername);

    @POST("User/ver/{email}")
    Call<Boolean> verifyemail(@Path("email") String email);

    @POST("User/password/{email}")
    Call<Boolean> verifypassword(@Path("email") String email);

    @PUT("User/password/{email}/{ver}/{password}")
    Call<Boolean> setpassword(@Path("email") String email,@Path("ver") String ver,@Path("password") String password);





    //post resources

    @GET("post/recent/{number}")
    Call<ArrayList<Post>> getresentposts(@Path("number") Integer number);

    @GET("post/like/{timestamp}")
    Call<ArrayList<Post>> getMostlikedpost(@Path("timestamp") String timestamp);

    @GET("post/child/{number}")
    Call<ArrayList<Post>> getAllchildPosts(@Path("number") Integer  parentPostId);

    @GET("post/own/{email}/security")
    Call<ArrayList<Post>> GetOwnPosts(@Header("authorization") String Auth,@Path("email") String email);

    @GET("post/ownLikedIdArray/{email}/security")
    Call<ArrayList<Integer>> GetOwnLikedPosts(@Header("authorization") String Auth,@Path("email") String email);

    @POST("post/{email}/security")
    Call<Post> creatpost(@Header("authorization")String Auth, @Path("email") String email, @Body Post thePost);

    @POST("post/like/{email}/{likedid}/security")
    Call<Boolean> likePost(@Header("authorization")String Auth,@Path("email") String email, @Path("likedid") Integer likepostid);



    @POST("post/answer/{email}/security")
    Call<Post> creatanswerpost(@Header("authorization")String Auth, @Path("email") String email, @Body Post thePost);

    @DELETE("post/deleteUserPosts/{email}/security")
    Call<Boolean> deleteUserPosts(@Header("authorization")String Auth, @Path("email") String email);


    @DELETE("post/delete/{email}/security")
    Call<Boolean>  DeletePost(@Header("authorization")String Auth, @Path("email") String email,Post post);

    @GET("post/search/{search}/security")
    Call<ArrayList<Post>> GetPostBySearch(@Path("search") String search);

    @DELETE("post/unlike/{email}/{unlikedid}/security")
    Call<Boolean> unlikePost(@Header("authorization")String Auth,@Path("email") String email, @Path("unlikedid") Integer unlikepostid);





}

