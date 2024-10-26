package com.example.prm392_homebuddy_app.model;

public class Service {
    public String name;
    public Double price;
    public int ImageUrl;

    public  String duration;
    public  String description;
    public String HelperName;
    public Service(String name, Double price, int imageUrl) {
        this.name = name;
        this.price = price;
        ImageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getHelperName() {
        return HelperName;
    }

    public void setHelperName(String helperName) {
        HelperName = helperName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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
