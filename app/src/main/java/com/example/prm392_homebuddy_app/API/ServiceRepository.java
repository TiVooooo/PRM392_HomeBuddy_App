package com.example.prm392_homebuddy_app.API;

public class ServiceRepository {
    public static ServiceService getServiceService(){
        return APIClient.getClient().create(ServiceService.class);
    }

    public static BookingAPI getBookingAPI(){
        return APIClient.getClient().create(BookingAPI.class);
    }

    public static CartAPI getCartAPI() {
        return APIClient.getClient().create(CartAPI.class);
    }
}
