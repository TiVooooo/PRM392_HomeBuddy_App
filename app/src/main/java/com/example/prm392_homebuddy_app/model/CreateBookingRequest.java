package com.example.prm392_homebuddy_app.model;

import java.io.Serializable;
import java.util.Date;

public class CreateBookingRequest implements Serializable {
    public Double price;
    public String address;
    public String phone;
    public String note;
    public Date bookingDate;
    public String  serviceDate;
    public int helperId;
    public int userId;

    public CreateBookingRequest(Double price, String address, String phone, String note, Date bookingDate, String serviceDate, int helperId, int userId) {
        this.price = price;
        this.address = address;
        this.phone = phone;
        this.note = note;
        this.bookingDate = bookingDate;
        this.serviceDate = serviceDate;
        this.helperId = helperId;
        this.userId = userId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public int getHelperId() {
        return helperId;
    }

    public void setHelperId(int helperId) {
        this.helperId = helperId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
