package com.example.prm392_homebuddy_app.API;

import android.util.Log;

import com.example.prm392_homebuddy_app.model.UserDTO;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

    public void updateUser(int userId, UserDTO user, UpdateUserCallback callback) {
        Call<Map<String, Object>> call = userAPI.updateUser(userId, user);

        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Trả về dữ liệu phản hồi
                    callback.onSuccess(response.body());
                } else {
                    // Nếu có lỗi, trả về thông báo lỗi
                    callback.onFailure("Failed to update user: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    public void updateAvatar(int userId, File avatarFile, UpdateAvatarCallback callback) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), avatarFile);

        // Tạo MultipartBody.Part
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", avatarFile.getName(), requestFile);

        Call<ResponseBody> call = userAPI.updateAvatar(userId, body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess("Avatar updated successfully");
                } else {
                    callback.onFailure("Failed to update avatar: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }
    public interface UpdateAvatarCallback {
        void onSuccess(String message);
        void onFailure(String errorMessage);
    }

    public interface UserDataCallback {
        void onSuccess(Map<String, Object> userData);
        void onFailure(String errorMessage);
    }

    public interface UpdateUserCallback {
        void onSuccess(Map<String, Object> responseData);  // Trả về dữ liệu phản hồi
        void onFailure(String errorMessage);
    }
}
