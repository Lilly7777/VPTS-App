package com.diploma.project.vpts.service;

import com.diploma.project.vpts.model.Device;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DevicesService {
    @POST("device")
    @Headers({"Content-Type: application/json"})
    Call<Device> registerDevice(@Header("Authorization") String auth, @Body Device device);

    @GET("device/{deviceId}")
    @Headers({"Content-Type: application/json"})
    Call<Device> getDevice(@Header("Authorization") String auth, @Path("deviceId") String deviceId);

    @DELETE("device/{deviceId}")
    @Headers({"Content-Type: application/json"})
    Call<Device> deleteDevice(@Header("Authorization") String auth, @Path("deviceId") String deviceId);

    @PUT("device/{deviceId}")
    @Headers({"Content-Type: application/json"})
    Call<Device> updateDevice(@Header("Authorization") String auth, @Path("deviceId") String deviceId, @Body Map<String, Object> body);
}
