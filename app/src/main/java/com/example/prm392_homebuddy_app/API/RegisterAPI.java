package com.example.prm392_homebuddy_app.API;

import com.example.prm392_homebuddy_app.model.RegisterHelperRequest;
import com.example.prm392_homebuddy_app.model.RegisterResponse;
import com.example.prm392_homebuddy_app.model.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterAPI {
    @POST("User")
    Call<RegisterResponse> register(@Body RegisterRequest request);
    @POST("User/create-helper")
    Call<RegisterResponse> registerHelper(@Body RegisterHelperRequest request);
}
