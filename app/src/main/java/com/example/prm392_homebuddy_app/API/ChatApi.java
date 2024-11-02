package com.example.prm392_homebuddy_app.API;

import com.example.prm392_homebuddy_app.model.Chat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChatApi {
    @GET("api/Chat/userid/{userId}")
    Call<List<Chat>> getUserChats(@Path("userId") int userId);
}
