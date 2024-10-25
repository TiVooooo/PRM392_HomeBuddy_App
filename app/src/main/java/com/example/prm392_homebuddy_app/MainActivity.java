package com.example.prm392_homebuddy_app;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.prm392_homebuddy_app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar.getRoot());  // This still sets the custom Toolbar as ActionBar

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
                } else {
                    title.setText("Title");
                }
            }
        });
    }
}
