package com.example.prm392_homebuddy_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_homebuddy_app.ui.order.OrderFragment;

public class OrderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order);

        Bundle bundle = new Bundle();
        bundle.putString("serviceName", getIntent().getStringExtra("serviceName"));
        bundle.putDouble("price", getIntent().getDoubleExtra("price", 0.0));

        OrderFragment orderFragment = new OrderFragment();
        orderFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, orderFragment)
                .commit();
    }
}
