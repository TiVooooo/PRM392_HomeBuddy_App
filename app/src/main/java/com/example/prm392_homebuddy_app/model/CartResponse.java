package com.example.prm392_homebuddy_app.model;

import java.util.List;

public class CartResponse {
    private List<Cart> cartItems;
    private double subtotal;

    public CartResponse(List<Cart> cartItems, double subtotal) {
        this.cartItems = cartItems;
        this.subtotal = subtotal;
    }

    public List<Cart> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Cart> cartItems) {
        this.cartItems = cartItems;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
