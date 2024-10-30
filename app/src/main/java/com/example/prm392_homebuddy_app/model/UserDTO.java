package com.example.prm392_homebuddy_app.model;

public class UserDTO {
    private String email;
    private String password; // Có thể không cần thiết nếu bạn không muốn lưu mật khẩu
    private String name;
    private String phone;
    private boolean gender; // true cho nam, false cho nữ
    private String address;
    private String avatar; // Thêm avatar
    private String role; // Thêm role
    private int parentId; // Thêm parentId

    // Constructor
    public UserDTO(String email, String password, String name, String phone, boolean gender, String address, String avatar, String role, int parentId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.address = address;
        this.avatar = avatar; // Khởi tạo avatar
        this.role = role; // Khởi tạo role
        this.parentId = parentId; // Khởi tạo parentId
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    // Các phương thức getter và setter còn lại...
}
