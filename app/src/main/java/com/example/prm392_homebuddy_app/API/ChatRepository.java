package com.example.prm392_homebuddy_app.API;

import android.util.Log;

import com.example.prm392_homebuddy_app.model.ChatResponse;
import com.example.prm392_homebuddy_app.model.MessageResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRepository {
    private ChatApi chatApi;

    public ChatRepository() {
        chatApi = APIClient.getClient().create(ChatApi.class);
    }

    public void getUserChats(int userId, ChatDataCallback<List<ChatResponse>> callback) {
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

    public void getMessagesFromChat(int chatId, ChatDataCallback<List<MessageResponse>> callback) {
        Call<List<MessageResponse>> call = chatApi.getMessagesFromChat(chatId);

        call.enqueue(new Callback<List<MessageResponse>>() {
            @Override
            public void onResponse(Call<List<MessageResponse>> call, Response<List<MessageResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    // Ghi log chi tiết thông tin phản hồi để kiểm tra
                    String errorBody = null;
                    try {
                        errorBody = response.errorBody() != null ? response.errorBody().string() : "No error body";
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Log.e("ChatRepository", "Response failed: " + response.message() + ", Code: " + response.code() + ", Body: " + errorBody);
                    callback.onFailure("Failed to fetch messages: " + response.message() + ", Code: " + response.code() + ", Body: " + errorBody);
                }
            }

            @Override
            public void onFailure(Call<List<MessageResponse>> call, Throwable t) {
                Log.e("ChatRepository", "Error occurred: " + t.getMessage());
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }


    public interface ChatDataCallback<T> {
        void onSuccess(T data);
        void onFailure(String errorMessage);
    }
}
