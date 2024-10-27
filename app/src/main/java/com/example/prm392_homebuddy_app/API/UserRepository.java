package com.example.prm392_homebuddy_app.API;

import android.util.Log;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private UserAPI userAPI;

    public UserRepository() {
        userAPI = APIClient.getClient().create(UserAPI.class);
    }

    public void getUserById(int userId, UserDataCallback callback) {
        Call<Map<String, Object>> call = userAPI.getUserById(userId);

        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, Object> userData = (Map<String, Object>) response.body().get("data");
                    callback.onSuccess(userData);
                } else {
                    callback.onFailure("Failed to fetch user: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    public interface UserDataCallback {
        void onSuccess(Map<String, Object> userData);
        void onFailure(String errorMessage);
    }
}
