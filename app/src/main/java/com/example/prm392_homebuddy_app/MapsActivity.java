package com.example.prm392_homebuddy_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.prm392_homebuddy_app.constants.Constants;
import com.example.prm392_homebuddy_app.setting.DirectionsJSONParser;
import com.example.prm392_homebuddy_app.setting.DownloadUrl;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private final int FINE_PERMISSION_CODE = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private GoogleMap myMap;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra(Constants.ADDRESS)) {
                //address = intent.getStringExtra(Constants.ADDRESS);
            }
        } else {
            Log.e("MapsActivity", "Map Fragment not found");
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        // Check for location permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
        } else {
            getCurrentLocation(); // Get the current location if permission is granted
        }

        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.setOnMapClickListener(latLng -> putMarker(latLng, "Selected Location"));
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(location -> {
            if (location != null) {
                currentLocation = location;
                LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                putMarker(currentLatLng, "Current Location");
                searchLocation(address, currentLatLng);
            } else {
                Log.e("MapsActivity", "Current location is null");
                Toast.makeText(this, "Unable to retrieve current location", Toast.LENGTH_SHORT).show();
                // Optionally, request location updates here
                requestLocationUpdates(); // Call to request location updates
            }
        });
    }

    private void requestLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000); // Update every 10 seconds
        locationRequest.setFastestInterval(5000); // Fastest update every 5 seconds

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        if (locationResult == null) {
                            return;
                        }
                        for (Location location : locationResult.getLocations()) {
                            if (location != null) {
                                currentLocation = location;
                                LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                                putMarker(currentLatLng, "Current Location");
                                searchLocation(address, currentLatLng);
                            }
                        }
                    }
                },
                Looper.getMainLooper());
    }

    private void searchLocation(String locationName, LatLng currentLatLng) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocationName(locationName, 1);
            if (addressList != null && !addressList.isEmpty()) {
                Address selectedAddress = addressList.get(0);
                LatLng destination = new LatLng(selectedAddress.getLatitude(), selectedAddress.getLongitude());
                putMarker(destination, selectedAddress.getAddressLine(0));
                drawRoute(currentLatLng, destination); // Draw route to destination
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error retrieving location", Toast.LENGTH_SHORT).show();
        }
    }

    private void putMarker(LatLng latLng, String title) {
        if (myMap != null) {
            myMap.clear(); // Clear previous markers
            myMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(title));
            myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15), 2000, null);
        } else {
            Log.e("MapsActivity", "myMap is null when trying to put marker");
        }
    }

    private void drawRoute(LatLng origin, LatLng destination) {
        String url = getDirectionsUrl(origin, destination);
        new FetchURL(myMap).execute(url);
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String mode = "mode=driving"; // Change this if you want walking or biking
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=YOUR_API_KEY"; // Replace with your API Key
        return url;
    }

    // Async Task to fetch data from the URL
    private class FetchURL extends AsyncTask<String, Void, String> {
        GoogleMap mMap;

        public FetchURL(GoogleMap mMap) {
            this.mMap = mMap;
        }

        @Override
        protected String doInBackground(String... strings) {
            String data = "";
            try {
                data = new DownloadUrl().readUrl(strings[0]);
            } catch (Exception e) {
                Log.e("FetchURL", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            new ParserTask(mMap).execute(result);
        }
    }

    // Class to parse the data
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        GoogleMap mMap;

        public ParserTask(GoogleMap mMap) {
            this.mMap = mMap;
        }

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                Log.e("ParserTask", e.toString());
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<PolylineOptions> polylineOptionsList = new ArrayList<>();
            for (List<HashMap<String, String>> path : result) {
                PolylineOptions options = new PolylineOptions();
                for (HashMap<String, String> point : path) {
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng latLng = new LatLng(lat, lng);
                    options.add(latLng);
                }
                options.width(10);
                options.color(Color.BLUE);
                polylineOptionsList.add(options);
            }

            for (PolylineOptions options : polylineOptionsList) {
                mMap.addPolyline(options);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation(); // Retry getting the current location if permission is granted
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}