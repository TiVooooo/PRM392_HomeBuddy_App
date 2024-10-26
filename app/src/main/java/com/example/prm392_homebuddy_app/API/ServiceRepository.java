package com.example.prm392_homebuddy_app.API;

public class ServiceRepository {
    public static  ServiceService getServiceService(){
        return APIClient.getClient().create(ServiceService.class);
    }
}
