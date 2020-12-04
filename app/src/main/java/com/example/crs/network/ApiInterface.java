package com.example.crs.network;

import com.example.crs.model.ResponseList;
import com.example.crs.model.StatusResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("/register")
        // API's endpoints
    Call<StatusResponse> registration(@Field("name") String name,
                                      @Field("email") String email,
                                      @Field("password") String password,
                                      @Field("phone_no") String phoneno);

    @GET("/")
    Call<Object> getUsersList();

    @FormUrlEncoded
    @POST("/login")
    Call<ResponseList> getLogininfo(@Field("email") String email, @Field("password") String password);
}
