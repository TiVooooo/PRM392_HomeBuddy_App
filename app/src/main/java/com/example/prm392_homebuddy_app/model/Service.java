package com.example.prm392_homebuddy_app.model;

public class Service {
    public String name;
    public Double price;
    public int ImageUrl;

    public Service(String name, Double price, int imageUrl) {
        this.name = name;
        this.price = price;
        ImageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(int imageUrl) {
        ImageUrl = imageUrl;
    }
}
