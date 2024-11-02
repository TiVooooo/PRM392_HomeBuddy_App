package com.example.prm392_homebuddy_app.ui.chat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.prm392_homebuddy_app.R;

public class SendMessageFragment extends Fragment {

    private EditText editTextMessage;
    private Button buttonSend;

    public SendMessageFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_message, container, false);

        editTextMessage = view.findViewById(R.id.editTextMessage);
        buttonSend = view.findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(v -> sendMessage());

        return view;
    }

    private void sendMessage() {
        String messageText = editTextMessage.getText().toString();
        if (!messageText.isEmpty()) {
            // Gọi hàm gửi tin nhắn ở đây (có thể thông qua callback hoặc ViewModel)
            // Ví dụ: sendMessageToServer(messageText);
            editTextMessage.setText(""); // Xóa trường nhập sau khi gửi
        }
    }
}
