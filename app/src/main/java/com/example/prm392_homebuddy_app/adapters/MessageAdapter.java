package com.example.prm392_homebuddy_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prm392_homebuddy_app.R;
import com.example.prm392_homebuddy_app.model.MessageResponse;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<MessageResponse> {
    private Context context;
    private List<MessageResponse> messages;
    private int currentUserId; // ID của người dùng hiện tại

    public MessageAdapter(Context context, int resource, List<MessageResponse> messages, int currentUserId) {
        super(context, resource, messages);
        this.context = context;
        this.messages = messages;
        this.currentUserId = currentUserId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        }

        MessageResponse message = messages.get(position);

        // Tìm các LinearLayout trong layout
        LinearLayout layoutSender = convertView.findViewById(R.id.layoutSender);
        LinearLayout layoutReceiver = convertView.findViewById(R.id.layoutReceiver);

        // Tìm các TextView trong layout
        TextView textViewMessageSender = convertView.findViewById(R.id.textViewMessageSender);
        TextView textViewTimeSender = convertView.findViewById(R.id.textViewTimeSender);
        TextView textViewMessageReceiver = convertView.findViewById(R.id.textViewMessageReceiver);
        //TextView textViewTimeReceiver = convertView.findViewById(R.id.textViewTimeReceiver);

        // Kiểm tra người gửi để hiển thị tin nhắn đúng
        if (message.getSenderId() == currentUserId) {
            // Tin nhắn của người gửi
            textViewMessageSender.setText(message.getMessageText());
            textViewTimeSender.setText(message.getSentTime().toString());

            // Hiển thị tin nhắn của người gửi và ẩn tin nhắn của người nhận
            layoutSender.setVisibility(View.VISIBLE);
            layoutReceiver.setVisibility(View.GONE);
        } else {
            // Tin nhắn của người nhận
            textViewMessageReceiver.setText(message.getMessageText());
            //textViewTimeReceiver.setText(message.getSentTime().toString());

            // Hiển thị tin nhắn của người nhận và ẩn tin nhắn của người gửi
            layoutReceiver.setVisibility(View.VISIBLE);
            layoutSender.setVisibility(View.GONE);
        }

        return convertView;
    }
}
