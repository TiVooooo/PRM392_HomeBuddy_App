package com.example.prm392_homebuddy_app.API;

import com.example.prm392_homebuddy_app.model.LoginRequest;
import com.example.prm392_homebuddy_app.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginAPI {
    @POST("Login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
