package com.example.prm392_homebuddy_app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.prm392_homebuddy_app.constants.Constants;
import com.example.prm392_homebuddy_app.model.BookingResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UserBookingDetailActivity extends AppCompatActivity {

    private TextView tvServiceDate, tvServiceTime, tvServiceName, tvAddress, tvUserName, tvHelperRating;
    private ImageView imgUserAvt;
    private Button btnBilling;
    private BookingResponse bookingResponse;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_booking_detail);

        tvServiceDate = findViewById(R.id.tvServiceDate);
        tvServiceTime = findViewById(R.id.tvServiceTime);
        tvServiceName = findViewById(R.id.tvServiceName);
        tvAddress = findViewById(R.id.tvAddress);
        tvUserName = findViewById(R.id.tvHelperName);
        tvHelperRating = findViewById(R.id.tvHelperRating);
        imgUserAvt = findViewById(R.id.imgHelperAvt);
        btnBilling = findViewById(R.id.btnBilling);

        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.DETAIL_USER_BOOKING)) {
            bookingResponse = (BookingResponse) intent.getSerializableExtra(Constants.DETAIL_USER_BOOKING);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                populateUI(bookingResponse);
            }
        }

        btnBilling.setOnClickListener(new View.OnClickListener() {
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
            tvHelperRating.setText(Integer.toString(bookingResponse.getRating()));

            String avatarUrl = bookingResponse.getHelperAvt();
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
        Intent mapIntent = new Intent(UserBookingDetailActivity.this, MapsActivity.class);
        mapIntent.putExtra(Constants.ADDRESS, address);
        this.startActivity(mapIntent);
    }
}
