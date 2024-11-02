package com.example.prm392_homebuddy_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_homebuddy_app.API.APIClient;
import com.example.prm392_homebuddy_app.API.BookingAPI;
import com.example.prm392_homebuddy_app.API.ServiceRepository;
import com.example.prm392_homebuddy_app.adapters.BookingAdapter;
import com.example.prm392_homebuddy_app.constants.Constants;
import com.example.prm392_homebuddy_app.model.BookingResponse;
import com.example.prm392_homebuddy_app.model.CreateBookingRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBookingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookingAdapter adapter;
    private BookingAPI bookingAPI;
    private ArrayList<BookingResponse> bookingList = new ArrayList<>();
    private int id;
    private Intent intent;
    private String adapterCond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_list);

        intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra(Constants.HELPER_ID)) {
                id = intent.getIntExtra(Constants.HELPER_ID, -1);
                adapterCond = Constants.HELPER_ID;
            } else if (intent.hasExtra(Constants.USER_ID)) {
                id = intent.getIntExtra(Constants.USER_ID, -1);
                adapterCond = Constants.USER_ID;
            }
        }

        bookingAPI = ServiceRepository.getBookingAPI();

        recyclerView = findViewById(R.id.recyclerViewBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BookingAdapter(this, adapterCond);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getAllBookingsByUser(id);

        if (intent != null) {
            if (intent.hasExtra(Constants.HELPER_ID)) {
                getAllBookingsByHelper(id);
            } else if (intent.hasExtra(Constants.USER_ID)) {
                getAllBookingsByUser(id);
            }
        }
    }

    private void getAllBookingsByHelper(int id) {
        try {
            Call<BookingResponse[]> call = bookingAPI.getAllBookingsByHelperId(id);
            call.enqueue(new Callback<BookingResponse[]>() {
                @Override
                public void onResponse(Call<BookingResponse[]> call, Response<BookingResponse[]> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        BookingResponse[] bookingResponses = response.body();
                        bookingList.clear();

                        for (BookingResponse bookingResponse : bookingResponses) {
                            bookingList.add(bookingResponse);
                        }

                        adapter.setTasks(bookingList);
                    } else {
                        Log.e("ListBookingActivity", "Error: " + response.code() + " " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<BookingResponse[]> call, Throwable t) {
                    Log.e("ListBookingActivity", "Network error: " + t.getMessage());
                }
            });
        } catch (Exception exception) {
            Log.e("ListBookingActivity", exception.getMessage());
        }
    }

    private void getAllBookingsByUser(int id) {
        try {
            Call<BookingResponse[]> call = bookingAPI.getAllBookingsByUserId(id);
            call.enqueue(new Callback<BookingResponse[]>() {
                @Override
                public void onResponse(Call<BookingResponse[]> call, Response<BookingResponse[]> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        BookingResponse[] bookingResponses = response.body();
                        bookingList.clear();

                        for (BookingResponse bookingResponse : bookingResponses) {
                            bookingList.add(bookingResponse);
                        }

                        adapter.setTasks(bookingList);
                    } else {
                        Log.e("ListBookingActivity", "Error: " + response.code() + " " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<BookingResponse[]> call, Throwable t) {
                    Log.e("ListBookingActivity", "Network error: " + t.getMessage());
                }
            });
        } catch (Exception exception) {
            Log.e("ListBookingActivity", exception.getMessage());
        }
    }
}
