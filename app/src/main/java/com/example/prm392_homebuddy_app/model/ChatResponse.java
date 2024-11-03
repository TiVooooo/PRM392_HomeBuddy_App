package com.example.prm392_homebuddy_app.model;

import java.util.List;

public class ChatResponse {
    private int id;
    private int senderId;
    private int receiverId;
    private String receiverName;
    private String receiverImageUrl;
    private String lastMessage;

    // Constructor
    public ChatResponse(int id, int senderId, int receiverId, String receiverName, String receiverImageUrl, String lastMessage) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.receiverImageUrl = receiverImageUrl;
        this.lastMessage = lastMessage;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverImageUrl() {
        return receiverImageUrl;
    }

    public void setReceiverImageUrl(String receiverImageUrl) {
        this.receiverImageUrl = receiverImageUrl;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

}