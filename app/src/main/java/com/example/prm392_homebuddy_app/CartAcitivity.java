package com.example.prm392_homebuddy_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_homebuddy_app.API.CartAPI;
import com.example.prm392_homebuddy_app.API.ServiceRepository;
import com.example.prm392_homebuddy_app.adapters.CartAdapter;
import com.example.prm392_homebuddy_app.model.Cart;
import com.example.prm392_homebuddy_app.model.CartResponse;
import com.example.prm392_homebuddy_app.ui.order.OrderFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CartAcitivity extends AppCompatActivity {

    private ListView listViewCart;
    private TextView subtotalTextView;
    private TextView total;
    private CartAdapter cartAdapter;
    private List<Cart> cartItems;
    private CartAPI cartService;
    private ImageView back;
    private Button btnCheckOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listViewCart = findViewById(R.id.listCart);
        subtotalTextView = findViewById(R.id.subtotal);
        total = findViewById(R.id.total);
        back = findViewById(R.id.back);
        btnCheckOut = findViewById(R.id.btnCheckOut);

        cartItems = new ArrayList<>();
        cartAdapter = new CartAdapter(this, cartItems);
        listViewCart.setAdapter(cartAdapter);

        cartService = ServiceRepository.getCartAPI();

        //chua co login userid
        int userId = 1;
        loadCartData(userId);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartAcitivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartAcitivity.this, CheckoutActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadCartData(int userId){
        try {
            Call<CartResponse> call = cartService.getCartItems(userId);

            call.enqueue(new Callback<CartResponse>() {
                @Override
                public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        CartResponse cartResponse = response.body();

                        cartItems.clear();
                        cartItems.addAll(cartResponse.getCartItems());
                        cartAdapter.notifyDataSetChanged();

                        subtotalTextView.setText("$" + cartResponse.getSubtotal());
                        total.setText("$" + cartResponse.getSubtotal());
                    }
                }

                @Override
                public void onFailure(Call<CartResponse> call, Throwable t) {
                    Toast.makeText(CartAcitivity.this, "Failed to load cart data", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception ex){
            Log.d("Loi", ex.getMessage());
        }
    }
}
