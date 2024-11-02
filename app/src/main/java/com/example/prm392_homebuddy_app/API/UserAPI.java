package com.example.prm392_homebuddy_app.API;
import com.example.prm392_homebuddy_app.model.UserDTO;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
public interface UserAPI {
    @GET("User/{id}")
    Call<Map<String, Object>> getUserById(@Path("id") int id);

    @PUT("User/{id}")
    Call<Map<String, Object>> updateUser(@Path("id") int id, @Body UserDTO user);

    @PUT("User/edit-avatar/{id}")
    Call<ResponseBody> updateAvatar(@Path("id") int id, @Part MultipartBody.Part avatar);
}
