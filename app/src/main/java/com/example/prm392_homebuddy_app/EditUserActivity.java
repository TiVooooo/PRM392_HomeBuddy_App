package com.example.prm392_homebuddy_app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditUserActivity extends AppCompatActivity {
    private ImageView imageViewAvatar;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextAddress;
    private Spinner spinnerGender;
    private EditText editTextRole;
    private EditText editTextParentId;
    private Button buttonUploadImage;
    private Button buttonSave;

    private static final int REQUEST_IMAGE_PICK = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        imageViewAvatar = findViewById(R.id.imageViewAvatar);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextAddress = findViewById(R.id.editTextAddress);
        spinnerGender = findViewById(R.id.spinnerGender);
        buttonUploadImage = findViewById(R.id.buttonUploadImage);
        buttonSave = findViewById(R.id.buttonSave);

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

        // Thiết lập spinner cho giới tính
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.gender_options, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerGender.setAdapter(adapter);
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
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
        boolean gender = spinnerGender.getSelectedItem().toString().equals("Male");
        String role = "1";
        int parentId = Integer.parseInt(editTextParentId.getText().toString().isEmpty() ? "0" : editTextParentId.getText().toString());

    }
}

