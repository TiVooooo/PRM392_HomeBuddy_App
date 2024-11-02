package com.example.prm392_homebuddy_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_homebuddy_app.API.ChatRepository;
import com.example.prm392_homebuddy_app.adapters.ChatAdapter;
import com.example.prm392_homebuddy_app.model.ChatResponse;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ListView listViewChats;
    private ChatAdapter chatAdapter;
    private List<ChatResponse> chatList = new ArrayList<>();
    private ChatRepository chatRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);  // Ensure this layout contains the ListView

        // Initialize the ListView
        listViewChats = findViewById(R.id.listViewChats);
        chatAdapter = new ChatAdapter(this, R.layout.item_chat, chatList);
        listViewChats.setAdapter(chatAdapter);

        // Initialize the ChatRepository
        chatRepository = new ChatRepository();
        loadChats(5);

        // Initialize the back button
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay về MainActivity
                Intent intent = new Intent(ChatActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listViewChats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    private void loadChats(int userId) {
        chatRepository.getUserChats(userId, new ChatRepository.ChatDataCallback() {
            @Override
            public void onSuccess(List<ChatResponse> chats) {
                Log.d("ChatActivity", "Fetched chats: " + chats);
                chatList.clear();
                chatList.addAll(chats);
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("ChatActivity", "Error fetching chats: " + errorMessage);
                Toast.makeText(ChatActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
