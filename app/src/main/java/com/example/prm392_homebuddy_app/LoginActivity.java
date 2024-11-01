package com.example.prm392_homebuddy_app;

import android.content.Intent;
import android.os.Bundle;
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
/*
FirebaseMessaging.getInstance().getToken()
    .addOnCompleteListener(task -> {
        if (!task.isSuccessful()) {
            Log.w("FCM", "Fetching FCM registration token failed", task.getException());
            return;
        }

        // Get new FCM registration token
        String deviceToken = task.getResult();
        Log.d("FCM", "Device Token: " + deviceToken);

        // Lưu deviceToken vào đăng nhập hoặc bộ nhớ dùng tạm thời để gửi khi đăng nhập
        // Bạn có thể lưu nó trong biến toàn cục hoặc PreferenceUtils chẳng hạn
    });

*/

}
