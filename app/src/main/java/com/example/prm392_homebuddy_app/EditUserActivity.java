//package com.example.prm392_homebuddy_app;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//
//public class EditUserActivity extends AppCompatActivity {
//
//    private EditText editTextEmail, editTextPassword, editTextName, editTextPhone, editTextAddress, editTextAvatar, editTextRole;
//    private RadioGroup radioGroupGender;
//    private Button buttonSave;
//
//    private int userId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_user);
//
//        // Khởi tạo các View
//        editTextEmail = findViewById(R.id.editTextEmail);
//        editTextPassword = findViewById(R.id.editTextPassword);
//        editTextName = findViewById(R.id.editTextName);
//        editTextPhone = findViewById(R.id.editTextPhone);
//        editTextAddress = findViewById(R.id.editTextAddress);
//        editTextAvatar = findViewById(R.id.editTextAvatar);
//        editTextRole = findViewById(R.id.editTextRole);
//        radioGroupGender = findViewById(R.id.radioGroupGender);
//        buttonSave = findViewById(R.id.buttonSave);
//
//        userId = getIntent().getIntExtra("USER_ID", -1); // Thay đổi tên "USER_ID" nếu cần
//
//        buttonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveUser();
//            }
//        });
//    }
//
//    private void saveUser() {
//        // Lấy dữ liệu từ các EditText
//        String email = editTextEmail.getText().toString();
//        String password = editTextPassword.getText().toString();
//        String name = editTextName.getText().toString();
//        String phone = editTextPhone.getText().toString();
//        String address = editTextAddress.getText().toString();
//        String avatar = editTextAvatar.getText().toString();
//        String role = editTextRole.getText().toString();
//        boolean gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().equals("Male");
//
//        UserDTO userDTO = new UserDTO(email, password, name, phone, gender, address, avatar, role);
//
//        UserService.updateUser(userId, userDTO);
//    }
//}
