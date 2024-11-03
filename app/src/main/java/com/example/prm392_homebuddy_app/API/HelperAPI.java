package com.example.prm392_homebuddy_app.API;

import com.example.prm392_homebuddy_app.model.CartResponse;
import com.example.prm392_homebuddy_app.model.Helper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HelperAPI {
    String HELPER = "Helper";

    @GET(HELPER + "/address/{cartId}")
    Call<Helper> getHelperAddress(@Path("cartId") int cartId);
}
