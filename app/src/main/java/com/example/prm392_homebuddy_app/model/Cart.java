package com.example.prm392_homebuddy_app.model;

import java.util.Date;

public class Cart {
    public int cartId;
    public int userId;
    public int serviceId;
    public int quantity;
    public double price;
    public Date createAt;
    private String serviceName;
    private double servicePrice;
    private String serviceImage;

    public Cart(int cartId, int userId, int serviceId, int quantity, double price, Date createAt, String serviceName, double servicePrice, String serviceImage) {
        this.cartId = cartId;
        this.userId = userId;
        this.serviceId = serviceId;
        this.quantity = quantity;
        this.price = price;
        this.createAt = createAt;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.serviceImage = serviceImage;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }
}
