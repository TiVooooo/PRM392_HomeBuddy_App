package com.example.prm392_homebuddy_app;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.prm392_homebuddy_app.API.BookingAPI;
import com.example.prm392_homebuddy_app.API.CreateOrder;
import com.example.prm392_homebuddy_app.API.HelperAPI;
import com.example.prm392_homebuddy_app.API.ServiceRepository;
import com.example.prm392_homebuddy_app.API.ServiceService;
import com.example.prm392_homebuddy_app.adapters.ServiceSliderAdapter;
import com.example.prm392_homebuddy_app.model.BookingResponse;
import com.example.prm392_homebuddy_app.model.Cart;
import com.example.prm392_homebuddy_app.model.Helper;
import com.example.prm392_homebuddy_app.model.ServiceRelatives;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class FinalBookingActivity extends AppCompatActivity {
    TextView lblZpTransToken, txtToken;

    private TextView textviewBlue;

    private ViewPager2 viewPager;
    private ServiceSliderAdapter adapter;
    private Handler slideHandler = new Handler(Looper.getMainLooper());
    private ServiceService serviceService;
    private HelperAPI helperAPI;

    private Button btnCheckOut;
    private BookingAPI bookingAPI;

    private int serviceId;
    private String helperAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_bill);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ZaloPaySDK.init(2553, Environment.SANDBOX);

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
        helperAPI = ServiceRepository.getHelperAPI();
        btnCheckOut = findViewById(R.id.btnOrder);

        Intent intent = getIntent();
        Cart selectedCartItem = (Cart) intent.getSerializableExtra("selectedItem");
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        String finalAddress = intent.getStringExtra("address");
        String serviceDate = intent.getStringExtra("serviceDate");
        String note = intent.getStringExtra("note");

        String address = "";
        if (finalAddress != null) {
            String[] addressParts = finalAddress.split(",");
            if (addressParts.length > 0) {
                address = addressParts[0];
            }
        }

        Call<Helper> call = helperAPI.getHelperAddress(selectedCartItem.getCartId());
        call.enqueue(new Callback<Helper>() {
            @Override
            public void onResponse(Call<Helper> call, Response<Helper> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Helper helperResponse = response.body();
                    helperAddress = helperResponse.getAddress();
                    Log.d("Helper Address", helperAddress);
                } else {
                    Log.e("API Error", "Code: " + response.code() + " Message: " + response.message());
                    Toast.makeText(FinalBookingActivity.this, "Response was not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Helper> call, Throwable t) {
                Log.e("API Error", "Error message: " + t.getMessage());
                Toast.makeText(FinalBookingActivity.this, "Failed to load helper address", Toast.LENGTH_SHORT).show();
            }
        });

        TextView serviceNameTextView = findViewById(R.id.serviceName);
        TextView totalTextView = findViewById(R.id.total);
        TextView addressTextView = findViewById(R.id.address);
        TextView fullAddressTextView = findViewById(R.id.fullAddress);

        addressTextView.setText(address);

        if (finalAddress != null) {
            int maxLength = 300;
            if (finalAddress.length() > maxLength) {
                finalAddress = finalAddress.substring(0, maxLength - 3) + "...";
            }
            fullAddressTextView.setText(finalAddress);
        }

        serviceNameTextView.setText(selectedCartItem.getServiceName());
        totalTextView.setText(String.format("$%.2f", selectedCartItem.getServicePrice()));

        ImageView imageCart = findViewById(R.id.imageCart);
        TextView titleTextView = findViewById(R.id.title);
        TextView priceTextView = findViewById(R.id.price);

        if (selectedCartItem != null) {
            titleTextView.setText(selectedCartItem.getServiceName());
            priceTextView.setText("$" + selectedCartItem.getServicePrice());

            loadImage(selectedCartItem.getServiceImage(), imageCart);
        }

        String userAddress = fullAddressTextView.getText().toString();
        calculateDistance(userAddress, helperAddress);

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateOrder orderApi = new CreateOrder();
                try {
                    JSONObject data = orderApi.createOrder(String.valueOf(selectedCartItem.getServicePrice()));
                    String code = data.getString("return_code");

                    if (code.equals("1")) {
                        String token =data.getString("zp_trans_token");
                        ZaloPaySDK.getInstance().payOrder(FinalBookingActivity.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                                Log.d("appTransID", appTransID);
                                Log.d("transToken", transToken);
                                Log.d("transactionId", transactionId);
                                Intent intent = new Intent(FinalBookingActivity.this, PaymentResult.class);
                                intent.putExtra("status", "success");
                                startActivity(intent);

                            }

                            @Override
                            public void onPaymentCanceled(String zpTransToken, String appTransID) {
                                Log.d("appTransID", appTransID);
                                Log.d("zpTransToken", zpTransToken);
                                Intent intent = new Intent(FinalBookingActivity.this, PaymentResult.class);
                                intent.putExtra("status", "cancel");
                                startActivity(intent);

                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                                Intent intent = new Intent(FinalBookingActivity.this, PaymentResult.class);
                                intent.putExtra("status", "error");
                                startActivity(intent);
                            }
                        });
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        //loadRelatedServices(serviceId);
    }

    private void loadImage(String imageUrl, ImageView imageView) {
        Glide.with(this)
                .load(imageUrl)
                .into(imageView);
    }

    private void calculateDistance(String userAddress, String helperAddress) {
        String apiKey = "AIzaSyC11YtVlIxOoBO1yeCLGpyT7dKqyndcdyA";
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="
                + Uri.encode(userAddress)
                + "&destination="
                + Uri.encode(helperAddress)
                + "&key="
                + apiKey;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray routes = jsonResponse.getJSONArray("routes");
                    if (routes.length() > 0) {
                        JSONObject route = routes.getJSONObject(0);
                        JSONArray legs = route.getJSONArray("legs");
                        if (legs.length() > 0) {
                            JSONObject leg = legs.getJSONObject(0);
                            String distance = leg.getJSONObject("distance").getString("text");
                            // Cập nhật TextView với khoảng cách
                            TextView distanceFromTextView = findViewById(R.id.distanceFrom);
                            distanceFromTextView.setText("Distance from you: " + distance);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("DistanceError", "Error fetching distance: " + error.getMessage());
            }
        });

        queue.add(stringRequest);
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }


}
