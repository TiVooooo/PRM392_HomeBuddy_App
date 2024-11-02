package com.example.prm392_homebuddy_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.prm392_homebuddy_app.API.ChatRepository;
import com.example.prm392_homebuddy_app.adapters.MessageAdapter;
import com.example.prm392_homebuddy_app.model.MessageResponse;
import com.example.prm392_homebuddy_app.ui.chat.SendMessageFragment;
import com.example.prm392_homebuddy_app.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private ListView listViewMessages;
    private MessageAdapter messageAdapter;
    private List<MessageResponse> messageList = new ArrayList<>();
    private ChatRepository chatRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        listViewMessages = findViewById(R.id.listViewMessages);


        String userId = PreferenceUtils.getUserId(this);
        int currentUserId = Integer.parseInt(userId);
        messageAdapter = new MessageAdapter(this, R.layout.item_message, messageList, currentUserId);
        listViewMessages.setAdapter(messageAdapter);

        chatRepository = new ChatRepository();

        if (savedInstanceState == null) {
            SendMessageFragment sendMessageFragment = new SendMessageFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, sendMessageFragment); // ID của ViewGroup chứa fragment
            transaction.commit();
        }

        // Get chatId from Intent
        int chatId = getIntent().getIntExtra("chatId", -1);
        if (chatId != -1) {
            loadMessages(chatId);
        } else {
            Toast.makeText(this, "Chat ID is invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadMessages(int chatId) {
        chatRepository.getMessagesFromChat(chatId, new ChatRepository.ChatDataCallback<List<MessageResponse>>() {
            @Override
            public void onSuccess(List<MessageResponse> messages) {
                Log.d("MessageActivity", "Fetched messages: " + messages);
                messageList.clear();
                messageList.addAll(messages);
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("MessageActivity", "Error fetching messages: " + errorMessage);
                Toast.makeText(MessageActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
