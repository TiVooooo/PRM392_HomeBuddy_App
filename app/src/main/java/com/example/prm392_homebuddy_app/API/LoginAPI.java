package com.example.prm392_homebuddy_app.API;

import com.example.prm392_homebuddy_app.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginAPI {
    @FormUrlEncoded
    @POST("Login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("deviceToken") String deviceToken
    );
}
