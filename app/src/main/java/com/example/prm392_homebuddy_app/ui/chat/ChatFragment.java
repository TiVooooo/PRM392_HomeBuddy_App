package com.example.prm392_homebuddy_app.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.prm392_homebuddy_app.API.ChatRepository;
import com.example.prm392_homebuddy_app.R;
import com.example.prm392_homebuddy_app.adapters.ChatAdapter;
import com.example.prm392_homebuddy_app.model.Chat;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    private ListView listViewChats;
    private ChatAdapter chatAdapter;
    private List<Chat> chatList = new ArrayList<>();
    private ChatRepository chatRepository;

    public ChatFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        listViewChats = view.findViewById(R.id.listViewChats);
        chatAdapter = new ChatAdapter(requireContext(), R.layout.item_chat, chatList);
        listViewChats.setAdapter(chatAdapter);

        chatRepository = new ChatRepository();
        int userId = 5;
        loadChats(userId);

        return view;
    }

    private void loadChats(int userId) {
        chatRepository.getUserChats(userId, new ChatRepository.ChatDataCallback() {
            @Override
            public void onSuccess(List<Chat> chats) {
                chatList.clear();
                chatList.addAll(chats);
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(requireContext(), "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

