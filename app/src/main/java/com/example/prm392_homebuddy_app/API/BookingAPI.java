package com.example.prm392_homebuddy_app.API;

import com.example.prm392_homebuddy_app.model.BookingResponse;
import com.example.prm392_homebuddy_app.model.CreateBookingRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BookingAPI {
    String BOOKING = "Booking";

    @POST(BOOKING)
    Call<BookingResponse> checkOut(@Body CreateBookingRequest createBookingRequest);

    @DELETE(BOOKING + "/{id}")
    Call<BookingResponse> deleteBooking(@Path("id") Object id);

    @GET(BOOKING)
    Call<BookingResponse[]> getAllBookings();

    @GET(BOOKING + "/{id}")
    Call<BookingResponse> getBookingById(@Path("id") Object id);

    @GET(BOOKING + "/all/helper/{helperId}")
    Call<BookingResponse[]> getAllBookingsByHelperId(@Path("helperId") Object id);

    @GET(BOOKING + "/user/{userId}")
    Call<BookingResponse[]> getAllBookingsByUserId(@Path("userId") Object id);
}
