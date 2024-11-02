package com.example.prm392_homebuddy_app.API;

import android.util.Log;

import com.example.prm392_homebuddy_app.model.Chat;

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
        Call<List<Chat>> call = chatApi.getUserChats(userId);

        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed to fetch chats: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    // Interface callback để xử lý phản hồi
    public interface ChatDataCallback {
        void onSuccess(List<Chat> chatList);
        void onFailure(String errorMessage);
    }
}
