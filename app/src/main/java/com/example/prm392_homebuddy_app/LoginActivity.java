package com.example.prm392_homebuddy_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.prm392_homebuddy_app.API.LoginService;
import com.example.prm392_homebuddy_app.MainActivity;
import com.example.prm392_homebuddy_app.R;
import com.example.prm392_homebuddy_app.model.LoginRequest;
import com.example.prm392_homebuddy_app.model.LoginResponse;
import com.example.prm392_homebuddy_app.utils.PreferenceUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Tag;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
    }
    public void viewRegisterClicked(View view) {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }
    public void viewRegisterHelperClicked(View view) {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterHelperActivity.class);
        startActivity(registerIntent);
    }
    public void onLoginClick(View view) {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String deviceToken = "";
        if(TextUtils.isEmpty(email)){
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
            editTextPassword.setError("Please enter your email");
            editTextPassword.requestFocus();
            return;
        }
        LoginRequest request = new LoginRequest(email,password,deviceToken);
        LoginService.getLoginAPI().login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.getStatus() == 1) {
                        LoginResponse.Data data = loginResponse.getData();

                        String token = data.getToken();
                        String role = data.getRole();
                        int userId = data.getUserId();
                        String expirationString = data.getExpiration();

                        // Chuyển expiration từ UTC sang mili giây
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                        long expirationTime;
                        try {
                            expirationTime = sdf.parse(expirationString).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Invalid expiration format", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Lưu thông tin đăng nhập
                        PreferenceUtils.setLoggedIn(LoginActivity.this, true);
                        PreferenceUtils.saveToken(LoginActivity.this, token, expirationTime, role, String.valueOf(userId));

                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        Toast.makeText(LoginActivity.this, "Login success!", Toast.LENGTH_SHORT).show();
                        mainIntent.putExtra("USER_ROLE", role);
                        if(role == "User"){
                            mainIntent.putExtra("user_id", userId);
                        } else if (role == "Helper") {
                            mainIntent.putExtra("helper_id", userId);
                        }

                        startActivity(mainIntent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed! Response Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("API Failure", t.getMessage());
                Toast.makeText(LoginActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
