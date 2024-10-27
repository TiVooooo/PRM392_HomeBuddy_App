package com.example.prm392_homebuddy_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_homebuddy_app.ui.order.OrderFragment;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Load your fragments or set up your checkout UI here
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new OrderFragment())
                    .commit();
        }
    }
}
