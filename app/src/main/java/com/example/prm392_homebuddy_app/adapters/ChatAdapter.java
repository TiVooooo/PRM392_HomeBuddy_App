package com.example.prm392_homebuddy_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prm392_homebuddy_app.R;
import com.example.prm392_homebuddy_app.model.Chat;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<Chat> {
    private int resourceLayout;
    private Context context;

    public ChatAdapter(Context context, int resource, List<Chat> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            view = vi.inflate(resourceLayout, null);
        }

        Chat chat = getItem(position);

        if (chat != null) {
            ImageView imageViewProfile = view.findViewById(R.id.imageViewProfile);
            TextView textViewReceiverName = view.findViewById(R.id.textViewReceiverName);
            TextView textViewLastMessage = view.findViewById(R.id.textViewLastMessage);

            textViewReceiverName.setText(chat.getReceiverName());
            textViewLastMessage.setText(chat.getMessages() != null ? chat.getMessages() : "No messages");

            // Có thể sử dụng Glide hoặc Picasso để load ảnh nếu có URL hình đại diện
        }

        return view;
    }
}
