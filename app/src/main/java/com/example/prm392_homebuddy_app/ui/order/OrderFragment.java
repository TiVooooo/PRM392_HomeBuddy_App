package com.example.prm392_homebuddy_app.ui.order;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prm392_homebuddy_app.API.BookingAPI;
import com.example.prm392_homebuddy_app.API.ServiceRepository;
import com.example.prm392_homebuddy_app.MainActivity;
import com.example.prm392_homebuddy_app.R;
import com.example.prm392_homebuddy_app.adapters.BookingAdapter;
import com.example.prm392_homebuddy_app.constants.Constants;
import com.example.prm392_homebuddy_app.model.BookingResponse;
import com.example.prm392_homebuddy_app.model.CreateBookingRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {
    private RecyclerView recyclerView;
    private BookingAdapter adapter;
    private BookingAPI bookingAPI;
    private ArrayList<BookingResponse> bookingList = new ArrayList<>();
    private int id;
    private String adapterCond;

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_list, container, false);

        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(Constants.HELPER_ID)) {
                id = args.getInt(Constants.HELPER_ID, -1);
                adapterCond = Constants.HELPER_ID;
            } else if (args.containsKey(Constants.USER_ID)) {
                id = args.getInt(Constants.USER_ID, -1);
                adapterCond = Constants.USER_ID;
            }
        }

        bookingAPI = ServiceRepository.getBookingAPI();

        recyclerView = view.findViewById(R.id.recyclerViewBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new BookingAdapter(getContext(), adapterCond);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (adapterCond != null) {
            if (adapterCond.equals(Constants.HELPER_ID)) {
                getAllBookingsByHelper(1);
            } else if (adapterCond.equals(Constants.USER_ID)) {
                getAllBookingsByUser(1);
            }
        } else {
            Log.e("OrderFragment", "adapterCond is null");
        }
    }

    private void getAllBookingsByHelper(int id) {
        try {
            Call<BookingResponse[]> call = bookingAPI.getAllBookingsByHelperId(1);
            call.enqueue(new Callback<BookingResponse[]>() {
                @Override
                public void onResponse(Call<BookingResponse[]> call, Response<BookingResponse[]> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        bookingList.clear();
                        for (BookingResponse bookingResponse : response.body()) {
                            bookingList.add(bookingResponse);
                        }
                        adapter.setTasks(bookingList);
                    } else {
                        Log.e("OrderFragment", "Error: " + response.code() + " " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<BookingResponse[]> call, Throwable t) {
                    Log.e("OrderFragment", "Network error: " + t.getMessage());
                }
            });
        } catch (Exception exception) {
            Log.e("OrderFragment", exception.getMessage());
        }
    }

    private void getAllBookingsByUser(int id) {
        try {
            Call<BookingResponse[]> call = bookingAPI.getAllBookingsByUserId(id);
            call.enqueue(new Callback<BookingResponse[]>() {
                @Override
                public void onResponse(Call<BookingResponse[]> call, Response<BookingResponse[]> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        bookingList.clear();
                        for (BookingResponse bookingResponse : response.body()) {
                            bookingList.add(bookingResponse);
                        }
                        adapter.setTasks(bookingList);
                    } else {
                        Log.e("OrderFragment", "Error: " + response.code() + " " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<BookingResponse[]> call, Throwable t) {
                    Log.e("OrderFragment", "Network error: " + t.getMessage());
                }
            });
        } catch (Exception exception) {
            Log.e("OrderFragment", exception.getMessage());
        }
    }

}