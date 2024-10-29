package com.example.prm392_homebuddy_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_homebuddy_app.ui.service_detail.ServiceDetailFragment;

public class ServiceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        // Load your fragments or set up your checkout UI here
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ServiceDetailFragment())
                    .commit();
        }

        int serviceId = getIntent().getIntExtra("serviceId", -1); // Retrieve serviceId from Intent

        ServiceDetailFragment fragment = ServiceDetailFragment.newInstance(serviceId);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment) // Replace with the correct fragment container ID
                .commit();
    }
}
