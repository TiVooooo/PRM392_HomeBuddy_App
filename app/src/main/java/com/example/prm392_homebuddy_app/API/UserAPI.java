package com.example.prm392_homebuddy_app.API;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface UserAPI {
    @GET("User/{id}")
    Call<Map<String, Object>> getUserById(@Path("id") int id);
}
