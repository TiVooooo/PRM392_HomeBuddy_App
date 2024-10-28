package com.example.prm392_homebuddy_app.API;

import com.example.prm392_homebuddy_app.model.Booking;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BookingAPI {
    String BOOKING = "Booking";

    @POST(BOOKING)
    Call<Booking> checkOut(@Body Booking booking);
}
