package com.example.prm392_homebuddy_app;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.prm392_homebuddy_app.API.BookingAPI;
import com.example.prm392_homebuddy_app.API.ServiceRepository;
import com.example.prm392_homebuddy_app.API.ServiceService;
import com.example.prm392_homebuddy_app.adapters.ServiceSliderAdapter;
import com.example.prm392_homebuddy_app.model.Booking;
import com.example.prm392_homebuddy_app.model.ServiceRelatives;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalBookingActivity extends AppCompatActivity {

    private TextView textviewBlue;

    private ViewPager2 viewPager;
    private ServiceSliderAdapter adapter;
    private Handler slideHandler = new Handler(Looper.getMainLooper());
    private ServiceService serviceService;

    private Button btnCheckOut;
    private BookingAPI bookingAPI;

    private int serviceId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_bill);

        viewPager = findViewById(R.id.viewPager);
        bookingAPI = ServiceRepository.getBookingAPI();

        textviewBlue = findViewById(R.id.textView11);
        String text = "By placing this order, you agree to our Term of Service and Operational Rules.";

        SpannableString spannableString = new SpannableString(text);
        int startTerm = text.indexOf("Term of Service");
        int endTerm = startTerm + "Term of Service".length();
        int startRules = text.indexOf("Operational Rules");
        int endRules = startRules + "Operational Rules".length();

        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), startTerm, endTerm, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), startRules, endRules, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textviewBlue.setText(spannableString);
        serviceService = ServiceRepository.getServiceService();
        btnCheckOut = findViewById(R.id.btnOrder);

        String serviceName = getIntent().getStringExtra("serviceName");
        double price = getIntent().getDoubleExtra("price", 0.0);
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String address = getIntent().getStringExtra("address");
        String serviceDate = getIntent().getStringExtra("serviceDate");
        String note = getIntent().getStringExtra("note");

        TextView serviceNameTextView = findViewById(R.id.serviceName);
        TextView priceTextView = findViewById(R.id.total);
        TextView addressTextView = findViewById(R.id.address);

        serviceNameTextView.setText(serviceName);
        priceTextView.setText(String.format("$%.2f", price));
        addressTextView.setText(address);

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Booking booking = new Booking(price, serviceDate, address, phone, note);
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
        });
        //loadRelatedServices(serviceId);
    }
/*
    private void loadRelatedServices(int serviceId) {
        Call<List<ServiceRelatives>> call = serviceService.getAllServices(1);

        call.enqueue(new Callback<List<ServiceRelatives>>() {
            @Override
            public void onResponse(Call<List<ServiceRelatives>> call, Response<List<ServiceRelatives>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ServiceRelatives> products = response.body();
                    setupSlider(products);
                }
            }

            @Override
            public void onFailure(Call<List<ServiceRelatives>> call, Throwable t) {

            }
        });
    }

    private void setupSlider(List<ServiceRelatives> products) {
        adapter = new ServiceSliderAdapter(products,this);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(slideRunnable);
                slideHandler.postDelayed(slideRunnable, 3000); // 3 giây
            }
        });
    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            int nextSlide = (viewPager.getCurrentItem() + 1) % adapter.getItemCount();
            viewPager.setCurrentItem(nextSlide, true);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(slideRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(slideRunnable, 3000); // 3 giây
    }
 */
}
