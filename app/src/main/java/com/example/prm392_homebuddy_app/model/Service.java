package com.example.prm392_homebuddy_app.model;

public class Service {
    public int id;
    public String name;
    public Double price;
    public String image;

    public int HelperId;

    public int getHelperId() {
        return HelperId;
    }

    public void setHelperId(int helperId) {
        HelperId = helperId;
    }

    public  String duration;
    public  String description;
    public String helperName;
    public Service(String name, Double price, String image) {
        this.name = name;
        this.price = price;
        image = image;
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

    public String getHelperName() {
        return helperName;
    }

    public void setHelperName(String helperName) {
        helperName = helperName;
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

    public String getImage() {
        return image;
    }

    public void setImage(int image) {
        image = image;
    }
}
