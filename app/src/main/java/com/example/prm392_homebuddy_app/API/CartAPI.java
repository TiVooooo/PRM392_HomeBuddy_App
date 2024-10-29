package com.example.prm392_homebuddy_app.API;

import com.example.prm392_homebuddy_app.model.Booking;
import com.example.prm392_homebuddy_app.model.Cart;
import com.example.prm392_homebuddy_app.model.CartResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CartAPI {
    String CARTS = "Cart";

    @GET(CARTS + "/{userId}")
    Call<CartResponse> getCartItems(@Path("userId") int userId);
}
