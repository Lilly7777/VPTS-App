package com.diploma.project.vpts.service;

import com.diploma.project.vpts.model.VPTSUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @POST("user")
    @Headers({"Content-Type: application/json"})
    Call<VPTSUser> createUser(@Header("Authorization") String auth, @Body VPTSUser user);

    @GET("user/{userId}")
    @Headers({"Content-Type: application/json"})
    Call<VPTSUser> getUser(@Header("Authorization") String auth, @Path("userId") String userId);

    @PUT("user/{userId}")
    @Headers({"Content-Type: application/json"})
    Call<VPTSUser> updateUser(@Header("Authorization") String auth, @Path("userId") String userId, @Body VPTSUser user);

    @DELETE("user/{userId}")
    @Headers({"Content-Type: application/json"})
    Call<VPTSUser> deleteUser(@Header("Authorization") String auth, @Path("userId") String userId);
}
