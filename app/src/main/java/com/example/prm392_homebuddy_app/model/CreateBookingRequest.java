package com.example.prm392_homebuddy_app.model;

import java.io.Serializable;
import java.util.Date;

public class CreateBookingRequest implements Serializable {
    public Double price;
    public Date bookingDate;
    public String  serviceDate;
    public String address;
    public String phone;
    public String note;
    public String serviceName;

    public CreateBookingRequest(Double price, String  serviceDate, String address, String phone, String note) {
        this.price = price;
        this.serviceDate = serviceDate;
        this.address = address;
        this.phone = phone;
        this.note = note;
    }

    public String  getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String  serviceDate) {
        this.serviceDate = serviceDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
