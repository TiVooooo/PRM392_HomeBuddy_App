package com.example.prm392_homebuddy_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.prm392_homebuddy_app.API.UserRepository;
import com.example.prm392_homebuddy_app.utils.PreferenceUtils;

import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {

    private TextView textViewName, textViewEmail, textViewPhone, textViewAddress, textViewRole, textViewCreatedAt;
    private ImageView imageViewAvatar;
    private UserRepository userRepository;
    private Button btnEditProfile, btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Ánh xạ các view
        imageViewAvatar = findViewById(R.id.imageViewAvatar);
        textViewName = findViewById(R.id.textViewName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewRole = findViewById(R.id.textViewRole);
        textViewCreatedAt = findViewById(R.id.textViewCreatedAt);
        btnEditProfile = findViewById(R.id.buttonEditProfile);
        btnSignOut = findViewById(R.id.buttonSignOut);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, EditUserActivity.class);
                startActivity(intent);
            }
        });
        btnSignOut.setOnClickListener(view -> signOut());
        userRepository = new UserRepository();

        String userId = PreferenceUtils.getUserId(this);
        fetchUserProfile(Integer.parseInt(userId)); // ID người dùng mẫu, thay đổi khi cần
    }

    private void fetchUserProfile(int userId) {
        userRepository.getUserById(userId, new UserRepository.UserDataCallback() {
            @Override
            public void onSuccess(Map<String, Object> userData) {
                // Hiển thị dữ liệu lên giao diện
                displayUserData(userData);
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(UserProfileActivity.this, "Failed to load profile: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayUserData(Map<String, Object> userData) {
        textViewName.setText((String) userData.get("name"));
        textViewEmail.setText((String) userData.get("email"));
        textViewPhone.setText((String) userData.get("phone"));
        textViewAddress.setText((String) userData.get("address"));
        textViewRole.setText((String) userData.get("role"));
        textViewCreatedAt.setText((String) userData.get("createdAt"));

        String avatarUrl = (String) userData.get("avatar");
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            Glide.with(this)
                    .load(avatarUrl)
                    .placeholder(R.drawable.ic_account)
                    .error(R.drawable.ic_account)
                    .into(imageViewAvatar);

        } else {
            imageViewAvatar.setImageResource(R.drawable.ic_account);
        }
    }
    private void signOut() {
        // Xóa token và trạng thái đăng nhập
        PreferenceUtils.setLoggedIn(this, false);
        PreferenceUtils.saveToken(this, null, 0,null,null);

        // Chuyển hướng về màn hình Login
        Intent loginIntent = new Intent(UserProfileActivity.this, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }
}
