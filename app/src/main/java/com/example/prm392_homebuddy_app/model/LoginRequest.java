package com.example.prm392_homebuddy_app.model;

public class LoginRequest {
    private String Email;
    private String Password;
    private String DeviceToken;

    public LoginRequest(String email, String password, String deviceToken) {
        Email = email;
        Password = password;
        DeviceToken = deviceToken;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        DeviceToken = deviceToken;
    }
}
