package com.example.prm392_homebuddy_app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_homebuddy_app.API.BookingAPI;
import com.example.prm392_homebuddy_app.API.ServiceRepository;
import com.example.prm392_homebuddy_app.model.BookingResponse;
import com.example.prm392_homebuddy_app.model.Cart;
import com.example.prm392_homebuddy_app.model.CreateBookingRequest;
import com.example.prm392_homebuddy_app.ui.order.OrderFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    private EditText edtDate, edtName, edtPhone, edtAddress, edtServiceDate, edtNote;
    private TextView tvTotalPrice;
    private ImageView backIcon;
    private Button btnPay;
    private Cart selectedItem;

    private BookingAPI bookingAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order);

        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);
        edtServiceDate = findViewById(R.id.edtServiceDate);
        edtNote = findViewById(R.id.edtNote);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnPay = findViewById(R.id.btnPay);
        backIcon = findViewById(R.id.back_icon); // Initialize backIcon here
        bookingAPI = ServiceRepository.getBookingAPI();

        Intent intent = getIntent();
        if (intent != null) {
            selectedItem = (Cart) intent.getSerializableExtra("selectedItem");
        }

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
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(OrderActivity.this,
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
            if (serviceDate == null) throw new ParseException("Date parsing failed", 0);
        } catch (NumberFormatException e) {
            Log.e("OrderActivity", "Invalid price format: " + priceString);
            return;
        } catch (ParseException e) {
            Log.e("OrderActivity", "Invalid date format: " + e.getMessage());
            return;
        }
        String formattedServiceDate = outputDateFormat.format(serviceDate);

        Intent billIntent = new Intent(OrderActivity.this, FinalBookingActivity.class);
        billIntent.putExtra("selectedItem", selectedItem);
        billIntent.putExtra("name", name);
        billIntent.putExtra("phone", phone);
        billIntent.putExtra("address", address);
        billIntent.putExtra("serviceDate", formattedServiceDate);
        billIntent.putExtra("note", note);

        startActivity(billIntent);
    }

    private void navigateBack() {
        Intent intent = new Intent(OrderActivity.this, MainActivity.class);
        intent.putExtra("navigateTo", "cartFragment");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
