package com.example.prm392_homebuddy_app.model;

import java.util.List;

public class Chat {
    private int id;
    private int senderId;
    private int receiverId;
    private String receiverName;
    private String messages;

    // Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSenderId() { return senderId; }
    public void setSenderId(int senderId) { this.senderId = senderId; }

    public int getReceiverId() { return receiverId; }
    public void setReceiverId(int receiverId) { this.receiverId = receiverId; }

    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

    public String getMessages() { return messages; }
    public void setMessages(String messages) { this.messages = messages; }
}