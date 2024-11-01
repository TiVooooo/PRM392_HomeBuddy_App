package com.example.prm392_homebuddy_app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392_homebuddy_app.ui.service_detail.ServiceDetailFragment;

import com.example.prm392_homebuddy_app.ViewModel.CheckoutViewModel;
import com.example.prm392_homebuddy_app.model.LoginResponse;
import com.example.prm392_homebuddy_app.ui.home.HomeFragment;
import com.example.prm392_homebuddy_app.ui.order.OrderFragment;
import com.example.prm392_homebuddy_app.utils.PreferenceUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.prm392_homebuddy_app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private CheckoutViewModel checkoutViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (PreferenceUtils.isLoggedIn(this) && !PreferenceUtils.isTokenExpired(this)) {
            String role = PreferenceUtils.getUserRole(this);
            Toast.makeText(MainActivity.this, role + " is logged in ", Toast.LENGTH_SHORT).show();
        }
        else if (!PreferenceUtils.isLoggedIn(this) && PreferenceUtils.isTokenExpired(this)) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
            return;
        }
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar.getRoot());  // This still sets the custom Toolbar as ActionBar

        checkoutViewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_cleanup,
                R.id.navigation_cart,
                R.id.navigation_order,
                R.id.navigation_account).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        String destination = getIntent().getStringExtra("navigateTo");
        if ("cartFragment".equals(destination)) {
            navController.navigate(R.id.navigation_cart);
        }

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination,
                                             @Nullable Bundle arguments) {
                TextView title = findViewById(R.id.toolbar_title);  // Reference to the TextView in the custom toolbar
                // Update the title based on the destination
                if (destination.getId() == R.id.navigation_home) {
                    title.clearComposingText();
                    title.setText("Home");
                } else if (destination.getId() == R.id.navigation_order) {
                    title.setText("Dashboard");
                } else if (destination.getId() == R.id.navigation_cleanup) {
                    title.clearComposingText();
                    title.setText("Cleaning Supplies");
                }else if(destination.getId() == R.id.navigation_account) {
                    title.clearComposingText();
                    title.setText("Profile");
                    Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                } else {
                    title.setText("Title");
                }
            }
        });
    }
}
