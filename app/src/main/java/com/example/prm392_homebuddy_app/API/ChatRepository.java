package com.example.prm392_homebuddy_app.API;

import android.util.Log;

import com.example.prm392_homebuddy_app.model.ChatResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRepository {
    private ChatApi chatApi;

    public ChatRepository() {
        chatApi = APIClient.getClient().create(ChatApi.class);
    }

    public void getUserChats(int userId, ChatDataCallback callback) {
        Call<List<ChatResponse>> call = chatApi.getUserChats(userId);

        call.enqueue(new Callback<List<ChatResponse>>() {
            @Override
            public void onResponse(Call<List<ChatResponse>> call, Response<List<ChatResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    Log.e("ChatRepository", "Response failed: " + response.message() + ", Code: " + response.code());
                    callback.onFailure("Failed to fetch chats: " + response.message() + ", Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ChatResponse>> call, Throwable t) {
                Log.e("ChatRepository", "Error occurred: " + t.getMessage());
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    public interface ChatDataCallback {
        void onSuccess(List<ChatResponse> chatList);
        void onFailure(String errorMessage);
    }
}

