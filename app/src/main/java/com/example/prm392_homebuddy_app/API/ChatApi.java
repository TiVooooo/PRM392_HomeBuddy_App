package com.example.prm392_homebuddy_app.API;

import com.example.prm392_homebuddy_app.model.ChatResponse;
import com.example.prm392_homebuddy_app.model.MessageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChatApi {
    @GET("Chat/userid")
    Call<List<ChatResponse>> getUserChats(@Query("userid") int userId);

    @GET("Chat/chatid")
    Call<List<MessageResponse>> getMessagesFromChat(@Query("chatid") int chatId);

}

