package com.example.prm392_homebuddy_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_homebuddy_app.API.RegisterService;
import com.example.prm392_homebuddy_app.model.RegisterHelperRequest;
import com.example.prm392_homebuddy_app.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterHelperActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword, editTextName, editTextPhone, editTextAddress, editTextSkill;
    private Spinner genderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register_helper);

        editTextEmail = findViewById(R.id.EditEmail);
        editTextPassword = findViewById(R.id.EditPassword);
        editTextName = findViewById(R.id.EditName);
        editTextPhone = findViewById(R.id.EditPhone);
        editTextAddress = findViewById(R.id.EditAddress);
        editTextSkill = findViewById(R.id.EditSkill);
        genderSpinner = findViewById(R.id.genderSpinner);

        findViewById(R.id.registerButton).setOnClickListener(this::onRegisterClick);
    }

    public void onRegisterClick(View view) {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String address = editTextAddress.getText().toString();
        String skill = editTextSkill.getText().toString();
        boolean gender = genderSpinner.getSelectedItem().toString().equals("Male");

        RegisterHelperRequest request = new RegisterHelperRequest(email, password, name, phone, gender, address, skill);

        RegisterService.getRegisterAPI().registerHelper(request).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RegisterResponse registerResponse = response.body();
                    Toast.makeText(RegisterHelperActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if (registerResponse.isSuccess()) {
                        finish();  // Đóng trang đăng ký
                    }
                } else {
                    Toast.makeText(RegisterHelperActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterHelperActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void viewLoginClicked(View view) {
        Intent registerIntent = new Intent(RegisterHelperActivity.this, LoginActivity.class);
        startActivity(registerIntent);
    }
}
