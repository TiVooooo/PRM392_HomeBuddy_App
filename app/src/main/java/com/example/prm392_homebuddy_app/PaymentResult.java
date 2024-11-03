package com.example.prm392_homebuddy_app;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_homebuddy_app.API.BookingAPI;
import com.example.prm392_homebuddy_app.API.ServiceRepository;
import com.example.prm392_homebuddy_app.model.BookingResponse;
import com.example.prm392_homebuddy_app.model.Cart;
import com.example.prm392_homebuddy_app.model.CreateBookingRequest;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentResult extends AppCompatActivity {
    ImageView imageView;
    TextView txtView;

    private BookingAPI bookingAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.payment_result);

        imageView = (ImageView)findViewById(R.id.imageIcon);
        txtView = (TextView) findViewById(R.id.txtNotification);

        bookingAPI = ServiceRepository.getBookingAPI();

        String status = getIntent().getStringExtra("status");
        Cart selectedCartItem = (Cart) getIntent().getSerializableExtra("selectedItem");
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String finalAddress = getIntent().getStringExtra("address");
        String serviceDate = getIntent().getStringExtra("serviceDate");
        String note = getIntent().getStringExtra("note");

        if(!TextUtils.isEmpty(status)) {
            switch (status) {
                case "success":
                    //imageView.setImageResource(R.drawable.success);

                    CreateBookingRequest createBookingRequest = new CreateBookingRequest(selectedCartItem.getServicePrice(), finalAddress, phone, note, new Date(2023, 1, 1), serviceDate, 1, selectedCartItem.getUserId());
                    Call<BookingResponse> call = bookingAPI.checkOut(createBookingRequest);
                    call.enqueue(new Callback<BookingResponse>() {
                        @Override
                        public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Log.d("OrderActivity", "Checkout successful: " + response.body().toString());
                                Toast.makeText(PaymentResult.this, "Checkout successful!", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("OrderActivity", "Error: " + response.code() + " " + response.message());
                                Toast.makeText(PaymentResult.this, "Checkout failed: " + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<BookingResponse> call, Throwable t) {
                            Log.e("OrderActivity", "Failure: " + t.getMessage());
                            Toast.makeText(PaymentResult.this, "Checkout failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    txtView.setText("Thanh toán đơn hàng thành công");
                    break;

                case "cancel":
                    //imageView.setImageResource(R.drawable.discard);
                    txtView.setText("Bạn đã hủy thanh toán");
                    break;

                case "error":
                    //imageView.setImageResource(R.drawable.warning);
                    txtView.setText("Thanh toán đơn hàng thất bại");
                    break;
            }
        }
    }
}
