package com.example.prm392_homebuddy_app.model;

public class ServiceRelatives {
    private int id;
    private String name;
    private double price;
    private int helperId;
    private String image;

    public ServiceRelatives(int id, String name, double price, int helperId, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.helperId = helperId;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getHelperId() {
        return helperId;
    }

    public void setHelperId(int helperId) {
        this.helperId = helperId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
