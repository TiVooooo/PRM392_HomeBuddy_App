package com.example.prm392_homebuddy_app.model;

public class LoginResponse {
    private String token;
    private String role;
    private int userId;
    private String expiration;
    private String deviceToken;

    public LoginResponse(String token, String role, int userId, String expiration, String deviceToken) {
        this.token = token;
        this.role = role;
        this.userId = userId;
        this.expiration = expiration;
        this.deviceToken = deviceToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}

