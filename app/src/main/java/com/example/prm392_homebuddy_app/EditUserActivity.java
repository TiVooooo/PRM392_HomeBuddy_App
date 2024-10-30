package com.example.prm392_homebuddy_app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.prm392_homebuddy_app.model.UserDTO;
import com.example.prm392_homebuddy_app.API.UserRepository;
import com.example.prm392_homebuddy_app.utils.PreferenceUtils;

import java.io.File;
import java.util.Map;

public class EditUserActivity extends AppCompatActivity {
    private ImageView imageViewAvatar;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextAddress;
    private Spinner spinnerGender;
    private Button buttonUploadImage;
    private Button buttonSave;

    private static final int REQUEST_IMAGE_PICK = 1;
    private Uri imageUri;
    private UserRepository userRepository;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        userRepository = new UserRepository();
        userId = Integer.parseInt(PreferenceUtils.getUserId(this));

        imageViewAvatar = findViewById(R.id.imageViewAvatar);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextAddress = findViewById(R.id.editTextAddress);
        spinnerGender = findViewById(R.id.spinnerGender);
        buttonUploadImage = findViewById(R.id.buttonUploadImage);
        buttonSave = findViewById(R.id.buttonSave);

        // Thiết lập spinner cho giới tính
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        // Tải thông tin người dùng
        loadUserInfo();

        buttonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }
        });
    }

    private void loadUserInfo() {
        userRepository.getUserById(userId, new UserRepository.UserDataCallback() {
            @Override
            public void onSuccess(Map<String, Object> userData) {
                displayUserData(userData);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("EditUserActivity", "Failed to load user profile: " + errorMessage);
                Toast.makeText(EditUserActivity.this, "Failed to load profile: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    private void displayUserData(Map<String, Object> userData) {
        if (userData == null) {
            Toast.makeText(EditUserActivity.this, "User data is null", Toast.LENGTH_SHORT).show();
            return;
        }

        editTextName.setText((String) userData.get("name"));
        editTextEmail.setText((String) userData.get("email"));
        editTextPhone.setText((String) userData.get("phone"));
        editTextAddress.setText((String) userData.get("address"));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                imageUri = data.getData();
                imageViewAvatar.setImageURI(imageUri);
            }
        }
    }

    private void saveUserInfo() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String address = editTextAddress.getText().toString();
        boolean gender = spinnerGender.getSelectedItemPosition() == 0;

        String avatar = "default_avatar_url"; // Thay thế bằng giá trị thực tế nếu có
        String role = "user"; // Thay thế bằng giá trị thực tế nếu có
        int parentId = 0;

        UserDTO user = new UserDTO(email, password, name, phone, gender, address, avatar, role, parentId);

        userRepository.updateUser(userId, user, new UserRepository.UpdateUserCallback() {
            @Override
            public void onSuccess(Map<String, Object> responseData) {
                Toast.makeText(EditUserActivity.this, "User info updated", Toast.LENGTH_SHORT).show();
                if (imageUri != null) {
                    updateAvatar();
                } else {
                    finish();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(EditUserActivity.this, "Failed to update user: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void updateAvatar() {
        File avatarFile = new File(imageUri.getPath());
        userRepository.updateAvatar(userId, avatarFile, new UserRepository.UpdateAvatarCallback() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(EditUserActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(EditUserActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
