package com.example.prm392_homebuddy_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prm392_homebuddy_app.R;
import com.example.prm392_homebuddy_app.model.ChatResponse;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<ChatResponse> {
    private int resourceLayout;
    private Context context;

    public ChatAdapter(Context context, int resource, List<ChatResponse> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resourceLayout, parent, false);
        }

        ChatResponse chat = getItem(position);

        if (chat != null) {
            ImageView imageViewProfile = view.findViewById(R.id.imageViewProfile);
            TextView textViewReceiverName = view.findViewById(R.id.textViewReceiverName);
            TextView textViewLastMessage = view.findViewById(R.id.textViewLastMessage);

            textViewReceiverName.setText(chat.getReceiverName());

            // Get the last message or default to "No messages"
            String lastMessage = (chat.getLastMessage() != null) ? chat.getLastMessage() : "No messages";
            textViewLastMessage.setText(lastMessage);

            Glide.with(context).load(chat.getReceiverImageUrl()).into(imageViewProfile);
        }

        return view;
    }

}
