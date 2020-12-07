package com.example.crs.network;

import com.example.crs.model.BookingReq;
import com.example.crs.model.MenuInfo;
import com.example.crs.model.MenuResponseRepo;
import com.example.crs.model.ResponseList;
import com.example.crs.model.StatusResponse;
import com.example.crs.model.VIewBookingRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("/register")
        // API's endpoints
    Call<StatusResponse> registration(@Field("name") String name,
                                      @Field("email") String email,
                                      @Field("password") String password,
                                      @Field("phone_no") String phoneno);

//    @GET("/")
//    Call<Object> getUsersList();

    @FormUrlEncoded
    @POST("login")
    Call<ResponseList> getLogininfo(@Field("email") String email, @Field("password") String password);

    @POST("menu")
    Call<MenuResponseRepo> getMenuInfo();

//    public interface TaskService{
//        @POST("/bookorder")
//        Call<StatusResponse> sendBookingInfo(@Body BookingReq request);
//    }

    @POST("bookorder")
    Call<StatusResponse> sendBookingInfo(@Body BookingReq request);

    @FormUrlEncoded
    @POST("vieworder")
    Call<VIewBookingRes> getBookingDetails(@Field("unique_id") String unique_id, @Field("role") String role);

}
