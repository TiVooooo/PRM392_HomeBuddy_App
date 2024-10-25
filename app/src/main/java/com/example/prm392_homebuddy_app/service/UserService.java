package com.example.prm392_homebuddy_app.service;

import com.example.prm392_homebuddy_app.model.UserDTO; // Nhớ import UserDTO
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class UserService {
    private static final String BASE_URL = "https://localhost:7177/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // Phương thức gọi API updateUser mà không sử dụng interface
    public static void updateUser(int id, UserDTO userDTO) {
        // Tạo một service tạm thời để gọi PUT
        Call<Void> call = getClient().create(ApiService.class).updateUser(id, userDTO);

        // Thực hiện gọi API
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Xử lý khi thành công
                    System.out.println("User updated successfully!");
                } else {
                    // Xử lý khi thất bại
                    System.out.println("Failed to update user: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Xử lý khi gặp lỗi
                System.out.println("Error: " + t.getMessage());
            }
        });
    }

    // Tạo một interface bên trong UserService cho PUT request
    interface ApiService {
        @PUT("api/User/{id}")
        Call<Void> updateUser(@Path("id") int id, @Body UserDTO userDTO);
    }
}
