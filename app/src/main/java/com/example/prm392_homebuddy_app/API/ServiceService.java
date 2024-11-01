package com.example.prm392_homebuddy_app.API;

import com.example.prm392_homebuddy_app.model.Service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceService {
    String SERVICES = "Service";

    @GET(SERVICES)
    Call<Service[]> getAllServices();

    @GET(SERVICES + "/{id}")
    Call<Service> getAllService(@Path("id") Object id);

    @POST(SERVICES)
    Call<Service> createService(@Body Service service);


    /*@PUT(TRAINEES + "/{id}")
    Call<Trainee> updateTrainees(@Path("id") Object id, @Body Trainee trainee);

    @DELETE(TRAINEES + "/{id}")
    Call<Trainee> deleteTrainees(@Path("id") Object id);*/
}
