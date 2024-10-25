package com.example.prm392_homebuddy_app.model;

public class UserDTO {
    private String email;
    private String password;
    private String name;
    private String phone;
    private boolean gender;
    private String address;
    private String avatar;
    private String role;

    public UserDTO(String email, String password, String name, String phone, boolean gender, String address, String avatar, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.address = address;
        this.avatar = avatar;
        this.role = role;
    }
}

