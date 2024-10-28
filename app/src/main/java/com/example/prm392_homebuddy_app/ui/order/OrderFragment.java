package com.example.prm392_homebuddy_app.ui.order;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
import com.example.prm392_homebuddy_app.model.Booking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {
    private EditText edtDate, edtName, edtPhone, edtAddress, edtServiceDate, edtNote;
    private TextView tvTotalPrice;
    private ImageView backIcon;
    private Button btnPay;
    private OrderViewModel mViewModel;
    private BookingAPI bookingAPI;

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        edtName = view.findViewById(R.id.edtName);
        edtPhone = view.findViewById(R.id.edtPhone);
        edtAddress = view.findViewById(R.id.edtAddress);
        edtServiceDate = view.findViewById(R.id.edtServiceDate);
        edtNote = view.findViewById(R.id.edtNote);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        btnPay = view.findViewById(R.id.btnPay);
        backIcon = view.findViewById(R.id.back_icon); // Initialize backIcon here
        bookingAPI = ServiceRepository.getBookingAPI();

        edtServiceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePayment();
            }
        });

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateBack();
            }
        });

        return view;
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                        edtServiceDate.setText(selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private void handlePayment() {
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String address = edtAddress.getText().toString();
        String serviceDateString = edtServiceDate.getText().toString();
        String note = edtNote.getText().toString();
        String priceString = tvTotalPrice.getText().toString();

        double price = 0.0;
        Date serviceDate;

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()); // ISO 8601 format

        try {
            price = Double.parseDouble(priceString);
            serviceDate = inputDateFormat.parse(serviceDateString);
        } catch (NumberFormatException e) {
            Log.e("CheckoutActivity", "Invalid price format: " + priceString);
            return;
        } catch (ParseException e) {
            Log.e("CheckoutActivity", "Invalid date format: " + e.getMessage());
            return;
        }
        String formattedServiceDate = outputDateFormat.format(serviceDate);
        Booking booking = new Booking(price, formattedServiceDate, address, phone, note);
        Call<Booking> call = bookingAPI.checkOut(booking);
        call.enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(Call<Booking> call, Response<Booking> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("CheckoutActivity", "Checkout successful: " + response.body().toString());
                } else {
                    Log.e("CheckoutActivity", "Error: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Booking> call, Throwable t) {
                Log.e("CheckoutActivity", "Failure: " + t.getMessage());
            }
        });
    }

    private void navigateBack() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("navigateTo", "cartFragment");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtDate = view.findViewById(R.id.edtBookingDate);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        String todayDate = day + "/" + (month + 1) + "/" + year;
        edtDate.setText(todayDate);

        edtDate.setFocusable(false);
        edtDate.setClickable(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        // TODO: Use the ViewModel
    }

}