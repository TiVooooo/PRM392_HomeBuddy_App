package com.example.prm392_homebuddy_app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.prm392_homebuddy_app.API.CartAPI;
import com.example.prm392_homebuddy_app.API.ServiceRepository;
import com.example.prm392_homebuddy_app.adapters.CartAdapter;
import com.example.prm392_homebuddy_app.constants.Constants;
import com.example.prm392_homebuddy_app.model.BookingResponse;
import com.example.prm392_homebuddy_app.model.Cart;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class BookingDetailActivity extends AppCompatActivity {

    private TextView tvServiceDate, tvServiceTime, tvServiceName, tvAddress, tvUserName;
    private ImageView imgUserAvt;
    private Button btnMap;
    private BookingResponse bookingResponse;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_detail);

        tvServiceDate = findViewById(R.id.tvServiceDate);
        tvServiceTime = findViewById(R.id.tvServiceTime);
        tvServiceName = findViewById(R.id.tvServiceName);
        tvAddress = findViewById(R.id.tvAddress);
        tvUserName = findViewById(R.id.tvUserName);
        imgUserAvt = findViewById(R.id.imgUserAvt);
        btnMap = findViewById(R.id.btnMap);

        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.DETAIL_HELPER_BOOKING)) {
            bookingResponse = (BookingResponse) intent.getSerializableExtra(Constants.DETAIL_HELPER_BOOKING);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                populateUI(bookingResponse);
            }
        }

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMapAddress(bookingResponse.getAddress());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void populateUI(BookingResponse bookingResponse) {
        String serviceDateTime = bookingResponse.getServiceDate();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime dateTime = LocalDateTime.parse(serviceDateTime, formatter);

            String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String formattedTime = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));

            tvServiceDate.setText(formattedDate);
            tvServiceTime.setText(formattedTime);
            tvServiceName.setText(bookingResponse.getServiceName());
            tvAddress.setText(bookingResponse.getAddress());
            tvUserName.setText(bookingResponse.getUserName());

            String avatarUrl = bookingResponse.getUserAvt();
            if (avatarUrl != null && !avatarUrl.isEmpty()) {
                Glide.with(this)
                        .load(avatarUrl)
                        .placeholder(R.drawable.ic_account)
                        .error(R.drawable.ic_account)
                        .into(imgUserAvt);

            } else {
                imgUserAvt.setImageResource(R.drawable.ic_account);
            }
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
    }

    private void viewMapAddress(String address) {
        Intent mapIntent = new Intent(BookingDetailActivity.this, MapsActivity.class);
        mapIntent.putExtra(Constants.ADDRESS, address);
        this.startActivity(mapIntent);
    }
}
